package com.anypluspay.payment.facade.acquiring.refund;

import com.anypluspay.commons.lang.BaseResult;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.biz.acquiring.AcquiringOrder;
import com.anypluspay.payment.domain.repository.AcquiringOrderRepository;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.biz.AcquiringOrderStatus;
import com.anypluspay.payment.types.biz.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wxj
 * 2025/5/19
 */
@Service
public class AcquiringRefundBuilder extends PaymentBuilder {

    @Autowired
    private AcquiringOrderRepository acquiringOrderRepository;

    /**
     * 构造交易订单
     *
     * @param request
     * @return
     */
    public AcquiringOrder buildTradeOrder(AcquiringRefundRequest request, AcquiringOrder origAcquiringOrder) {
        Money refundAmount = new Money(request.getAmount(), origAcquiringOrder.getAmount().getCurrency());
        checkRefundAmount(origAcquiringOrder, refundAmount);
        AcquiringOrder acquiringOrder = new AcquiringOrder();
        acquiringOrder.setPaymentId(idGeneratorService.genPaymentId(origAcquiringOrder.getPaymentId(), IdType.TRADE_ORDER_ID));
        acquiringOrder.setRelationPaymentId(origAcquiringOrder.getPaymentId());
        acquiringOrder.setPartnerId(origAcquiringOrder.getPartnerId());
        acquiringOrder.setOutTradeNo(request.getOutTradeNo());
        acquiringOrder.setTradeType(TradeType.REFUND_ACQUIRING);
        acquiringOrder.setAmount(refundAmount);
        acquiringOrder.setSubject(origAcquiringOrder.getSubject());
        acquiringOrder.setPayeeId(origAcquiringOrder.getPayeeId());
        acquiringOrder.setPayeeAccountNo(origAcquiringOrder.getPayeeAccountNo());
        acquiringOrder.setPayerId(origAcquiringOrder.getPayerId());
        acquiringOrder.setStatus(AcquiringOrderStatus.INIT);
        return acquiringOrder;
    }

    private void checkRefundAmount(AcquiringOrder origAcquiringOrder, Money amount) {
        List<AcquiringOrder> allRelationOrders = acquiringOrderRepository.loadByRelationPaymentId(origAcquiringOrder.getPaymentId());
        if (!CollectionUtils.isEmpty(allRelationOrders)) {
            Money refundAmount = allRelationOrders.stream()
                    .filter(r -> r.getStatus().equals(AcquiringOrderStatus.SUCCESS) && r.getTradeType() == TradeType.REFUND_ACQUIRING)
                    .map(AcquiringOrder::getAmount)
                    .reduce(new Money(), Money::add);
            Assert.isTrue(!amount.greaterThan(origAcquiringOrder.getAmount().subtract(refundAmount)), "退款金额超出可退金额");
        }
    }

    /**
     * 构造交易退款响应
     *
     * @param acquiringOrder
     * @return
     */
    public AcquiringRefundResponse buildResponse(AcquiringOrder acquiringOrder) {
        AcquiringRefundResponse response = new AcquiringRefundResponse();
        response.setSuccess(true);
        response.setPaymentId(acquiringOrder.getPaymentId());
        response.setPartnerId(acquiringOrder.getPartnerId());
        response.setOutTradeNo(acquiringOrder.getOutTradeNo());
        response.setOrderStatus(acquiringOrder.getStatus().getCode());
        return response;
    }

    /**
     * 构造交易创建响应
     *
     * @param request
     * @param e
     * @return
     */
    public AcquiringRefundResponse buildExceptionResponse(AcquiringRefundRequest request, Exception e) {
        AcquiringRefundResponse response = new AcquiringRefundResponse();
        response.setPartnerId(request.getPartnerId());
        response.setOutTradeNo(request.getOutTradeNo());
        BaseResult.fillExceptionResult(response, e);
        return response;
    }
}