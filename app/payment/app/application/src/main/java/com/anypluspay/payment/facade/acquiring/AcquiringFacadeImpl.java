package com.anypluspay.payment.facade.acquiring;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.application.AbstractPaymentService;
import com.anypluspay.payment.domain.biz.acquiring.AcquiringOrder;
import com.anypluspay.payment.domain.process.PayProcess;
import com.anypluspay.payment.domain.process.refund.RefundApplyService;
import com.anypluspay.payment.domain.process.refund.RefundProcess;
import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateBuilder;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateRequest;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateResponse;
import com.anypluspay.payment.facade.acquiring.create.AcquiringPayBuilder;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayRequest;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayResponse;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayValidator;
import com.anypluspay.payment.facade.acquiring.query.TradeResponseBuilder;
import com.anypluspay.payment.facade.acquiring.refund.AcquiringRefundBuilder;
import com.anypluspay.payment.facade.acquiring.refund.AcquiringRefundRequest;
import com.anypluspay.payment.facade.acquiring.refund.AcquiringRefundResponse;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.biz.AcquiringOrderStatus;
import com.anypluspay.payment.types.biz.TradeType;
import com.anypluspay.payment.types.pay.RefundType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收单支付
 *
 * @author wxj
 * 2025/5/17
 */
@RestController
@Slf4j
public class AcquiringFacadeImpl extends AbstractPaymentService implements AcquiringFacade {

    @Autowired
    private AcquiringOrderRepository acquiringOrderRepository;

    @Autowired
    private AcquiringCreateBuilder acquiringCreateBuilder;

    @Autowired
    private AcquiringPayBuilder acquiringPayBuilder;

    @Autowired
    private AcquiringRefundBuilder acquiringRefundBuilder;

    @Autowired
    private RefundApplyService refundApplyService;

    @Autowired
    private AcquiringPayValidator acquiringPayValidator;

    @Override
    public AcquiringCreateResponse create(AcquiringCreateRequest request) {
        AcquiringCreateResponse response;
        try {
            AssertUtil.notNull(EnumUtil.getByCode(TradeType.class, request.getTradeType()), "交易类型不存在");
            AcquiringOrder acquiringOrder = acquiringOrderRepository.load(request.getOutTradeNo(), request.getPartnerId());
            AssertUtil.isNull(acquiringOrder, "订单已存在");
            acquiringOrder = acquiringCreateBuilder.buildTradeOrder(request);
            acquiringOrderRepository.store(acquiringOrder);
            response = acquiringCreateBuilder.buildResponse(acquiringOrder);
        } catch (Exception e) {
            log.error("创建交易异常", e);
            response = acquiringCreateBuilder.buildResponse(request, e);
        }
        return response;
    }

    @Override
    public AcquiringPayResponse pay(AcquiringPayRequest request) {
        AcquiringPayResponse response;
        try {
            PayProcess payOrder = savePayOrder(request);
            PayResult result = payProcessService.process(payOrder);
            response = acquiringPayBuilder.buildResponse(acquiringOrderRepository.load(payOrder.getPaymentId()), result);
        } catch (Exception e) {
            log.error("支付异常", e);
            response = acquiringPayBuilder.buildExceptionResponse(loadTradeOrder(request.getPaymentId(), request.getPartnerId(), request.getOutTradeNo()), e);
        }
        return response;
    }

    @Override
    public AcquiringRefundResponse refund(AcquiringRefundRequest request) {
        AcquiringRefundResponse response;
        try {
            RefundProcess refundOrder = saveRefundOrder(request);
            refundApplyService.apply(refundOrder);
            response = acquiringRefundBuilder.buildResponse(acquiringOrderRepository.load(refundOrder.getPaymentId()));
        } catch (Exception e) {
            log.error("退款异常", e);
            response = acquiringRefundBuilder.buildExceptionResponse(request, e);
        }
        return response;
    }

    @Override
    public TradeResponse queryByPaymentId(String paymentId) {
        AcquiringOrder acquiringOrder = loadTradeOrder(paymentId, null, null);
        if (acquiringOrder == null) {
            return TradeResponseBuilder.buildNotExists();
        }
        return TradeResponseBuilder.build(acquiringOrder);
    }

    @Override
    public TradeResponse queryByPartner(String partnerId, String outTradeNo) {
        AcquiringOrder acquiringOrder = loadTradeOrder(null, partnerId, outTradeNo);
        if (acquiringOrder == null) {
            return TradeResponseBuilder.buildNotExists();
        }
        return TradeResponseBuilder.build(acquiringOrder);
    }

    private PayProcess savePayOrder(AcquiringPayRequest request) {
        return transactionTemplate.execute(status -> {
            AcquiringOrder acquiringOrder = lockTradeOrder(request.getPaymentId(), request.getOutTradeNo(), request.getPartnerId());
            acquiringPayValidator.validate(acquiringOrder);
            PayProcess payOrder = acquiringPayBuilder.buildPayProcess(acquiringOrder, request);
            acquiringOrder.setPayOrderId(payOrder.getProcessId());
            acquiringOrder.setStatus(AcquiringOrderStatus.PAYING);
            acquiringOrderRepository.reStore(acquiringOrder);
            payProcessRepository.store(payOrder);
            return payOrder;
        });
    }

    /**
     * 保存退款指令
     *
     * @param request
     * @return
     */
    private RefundProcess saveRefundOrder(AcquiringRefundRequest request) {
        return transactionTemplate.execute(status -> {
            AcquiringOrder origAcquiringOrder = lockTradeOrder(request.getOrigPaymentId(), request.getOrigOutTradeNo(), request.getPartnerId());
            AssertUtil.notNull(origAcquiringOrder, "原订单不存在");
            AcquiringOrder refundAcquiringOrder = acquiringRefundBuilder.buildTradeOrder(request, origAcquiringOrder);
            PayProcess originalPayOrder = payProcessRepository.load(origAcquiringOrder.getPayOrderId());
            RefundProcess refundOrder = refundApplyService.buildRefundOrder(refundAcquiringOrder.getPaymentId(), new Money(request.getAmount(), origAcquiringOrder.getAmount().getCurrency()), RefundType.BIZ_REQUEST, originalPayOrder);
            refundAcquiringOrder.setPayOrderId(refundOrder.getProcessId());
            acquiringOrderRepository.store(refundAcquiringOrder);
            refundProcessRepository.store(refundOrder);
            return refundOrder;
        });
    }

    private AcquiringOrder loadTradeOrder(String paymentId, String partnerId, String outTradeNo) {
        AcquiringOrder acquiringOrder;
        if (StringUtils.isNotBlank(paymentId)) {
            acquiringOrder = acquiringOrderRepository.load(paymentId);
        } else {
            AssertUtil.isTrue(StringUtils.isNotBlank(partnerId) && StringUtils.isNotBlank(outTradeNo), "支付单号或外部交易单号至少传一个");
            acquiringOrder = acquiringOrderRepository.load(partnerId, outTradeNo);
        }
        return acquiringOrder;
    }

    private AcquiringOrder lockTradeOrder(String paymentId, String partnerId, String outTradeNo) {
        AcquiringOrder acquiringOrder;
        if (StringUtils.isNotBlank(paymentId)) {
            acquiringOrder = acquiringOrderRepository.lock(paymentId);
        } else {
            AssertUtil.isTrue(StringUtils.isNotBlank(partnerId) && StringUtils.isNotBlank(outTradeNo), "支付单号或外部交易单号至少传一个");
            acquiringOrder = acquiringOrderRepository.lock(partnerId, outTradeNo);
        }
        return acquiringOrder;
    }

}
