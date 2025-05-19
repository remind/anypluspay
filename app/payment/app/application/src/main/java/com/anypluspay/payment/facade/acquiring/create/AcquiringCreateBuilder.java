package com.anypluspay.payment.facade.acquiring.create;

import com.anypluspay.commons.lang.BaseResult;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.application.PaymentBuilder;
import com.anypluspay.payment.domain.trade.AcquiringOrder;
import com.anypluspay.payment.types.status.TradeOrderStatus;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.TradeType;
import org.springframework.stereotype.Service;

/**
 * 交易创建构造器
 * @author wxj
 * 2025/5/17
 */
@Service
public class AcquiringCreateBuilder extends PaymentBuilder {

    /**
     * 构造交易订单
     * @param request
     * @return
     */
    public AcquiringOrder buildTradeOrder(AcquiringCreateRequest request) {
        AcquiringOrder acquiringOrder = new AcquiringOrder();
        acquiringOrder.setPaymentId(idGeneratorService.genPaymentId(request.getPartnerId(), IdType.TRADE_ORDER_ID));
        acquiringOrder.setPartnerId(request.getPartnerId());
        acquiringOrder.setOutTradeNo(request.getOutTradeNo());
        acquiringOrder.setTradeType(EnumUtil.getByCode(TradeType.class, request.getTradeType()));
        acquiringOrder.setAmount(request.getAmount());
        acquiringOrder.setSubject(request.getSubject());
        acquiringOrder.setPayeeId(request.getPayeeId());
        acquiringOrder.setPayeeAccountNo(request.getPayeeAccountNo());
        acquiringOrder.setPayerId(request.getPayerId());
        acquiringOrder.setExtension(new Extension(request.getExtension()));
        acquiringOrder.setStatus(TradeOrderStatus.INIT);
        return acquiringOrder;
    }

    /**
     * 构造交易创建响应
     * @param acquiringOrder
     * @return
     */
    public AcquiringCreateResponse buildResponse(AcquiringOrder acquiringOrder) {
        AcquiringCreateResponse response = new AcquiringCreateResponse();
        response.setSuccess(true);
        response.setPaymentId(acquiringOrder.getPaymentId());
        response.setPartnerId(acquiringOrder.getPartnerId());
        response.setOutTradeNo(acquiringOrder.getOutTradeNo());
        return response;
    }

    /**
     * 构造交易创建响应
     * @param request
     * @param e
     * @return
     */
    public AcquiringCreateResponse buildResponse(AcquiringCreateRequest request, Exception e) {
        AcquiringCreateResponse response = new AcquiringCreateResponse();
        response.setPartnerId(request.getPartnerId());
        response.setOutTradeNo(request.getOutTradeNo());
        BaseResult.fillExceptionResult(response, e);
        return response;
    }

}
