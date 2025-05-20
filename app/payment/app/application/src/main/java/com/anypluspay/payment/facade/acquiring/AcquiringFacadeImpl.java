package com.anypluspay.payment.facade.acquiring;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.payment.application.AbstractPaymentService;
import com.anypluspay.payment.domain.process.PayProcess;
import com.anypluspay.payment.domain.process.refund.RefundProcess;
import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.domain.biz.acquiring.AcquiringOrder;
import com.anypluspay.payment.types.biz.AcquiringOrderStatus;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateBuilder;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateRequest;
import com.anypluspay.payment.facade.acquiring.create.AcquiringCreateResponse;
import com.anypluspay.payment.facade.acquiring.create.AcquiringPayBuilder;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayRequest;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayResponse;
import com.anypluspay.payment.facade.acquiring.query.TradeResponseBuilder;
import com.anypluspay.payment.facade.acquiring.refund.AcquiringRefundBuilder;
import com.anypluspay.payment.facade.acquiring.refund.AcquiringRefundRequest;
import com.anypluspay.payment.facade.acquiring.refund.AcquiringRefundResponse;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.status.PayProcessStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
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

    @Override
    public AcquiringCreateResponse create(AcquiringCreateRequest request) {
        AcquiringCreateResponse response;
        try {
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
            refundService.process(refundOrder);
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
            AssertUtil.notNull(acquiringOrder, "订单不存在");
            AssertUtil.isTrue(acquiringOrder.getStatus() == AcquiringOrderStatus.INIT || acquiringOrder.getStatus() == AcquiringOrderStatus.PAYING, "订单状态为非待支付");
            PayProcess payOrder = acquiringPayBuilder.buildPayProcess(acquiringOrder, request);
            payProcessRepository.store(payOrder);
            return payOrder;
        });
    }

    private RefundProcess saveRefundOrder(AcquiringRefundRequest request) {
        return transactionTemplate.execute(status -> {
            AcquiringOrder origAcquiringOrder = lockTradeOrder(request.getOrigPaymentId(), request.getOrigOutTradeNo(), request.getPartnerId());
            AssertUtil.notNull(origAcquiringOrder, "原订单不存在");
            AcquiringOrder refundAcquiringOrder = acquiringRefundBuilder.buildTradeOrder(request, origAcquiringOrder);
            PayProcess originalPayOrder = payProcessRepository.loadByPaymentId(origAcquiringOrder.getPaymentId()).stream()
                    .filter(payOrder -> payOrder.getStatus() == PayProcessStatus.SUCCESS).findFirst().get();
            RefundProcess refundOrder = acquiringRefundBuilder.buildRefundOrder(refundAcquiringOrder, new Money(request.getAmount(), origAcquiringOrder.getAmount().getCurrency()), originalPayOrder);
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
            acquiringOrder = acquiringOrderRepository.load(partnerId, outTradeNo);
        }
        return acquiringOrder;
    }

    private AcquiringOrder lockTradeOrder(String paymentId, String partnerId, String outTradeNo) {
        AcquiringOrder acquiringOrder;
        if (StringUtils.isNotBlank(paymentId)) {
            acquiringOrder = acquiringOrderRepository.lock(paymentId);
        } else {
            acquiringOrder = acquiringOrderRepository.lock(partnerId, outTradeNo);
        }
        return acquiringOrder;
    }

}
