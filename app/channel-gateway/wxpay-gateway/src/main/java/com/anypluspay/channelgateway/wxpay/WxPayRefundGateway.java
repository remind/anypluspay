package com.anypluspay.channelgateway.wxpay;

import com.anypluspay.channelgateway.api.refund.RefundGateway;
import com.anypluspay.channelgateway.api.refund.RefundGatewayOrder;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.commons.lang.types.Money;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/9/18
 */
@Service
@Slf4j
public class WxPayRefundGateway extends AbstractWxPayGateway implements RefundGateway {

    @Override
    public void refund(GatewayRequest<RefundGatewayOrder> gatewayRequest, RefundGatewayOrder refundOrder, GatewayResult result) {
        WxPayConfig wxPayConfig = getWxPayConfig();
        requestWrapper((Void) -> {
            RefundService refundService = getRefundService(wxPayConfig);
            CreateRequest request = new CreateRequest();
            request.setOutTradeNo(refundOrder.getOrigInstRequestNo());
            request.setOutRefundNo(refundOrder.getInstRequestNo());
            request.setReason(refundOrder.getReason());
            AmountReq amountReq = new AmountReq();
            amountReq.setRefund(refundOrder.getAmount().getCent());
            request.setAmount(amountReq);
            Refund refund = refundService.create(request);
            result.setRealAmount(new Money(0, refund.getAmount().getRefund().intValue()));
            result.setInstResponseNo(refund.getRefundId());
            result.setApiCode(refund.getStatus().name());
        }, result);
        result.setSuccess(true);
    }

}
