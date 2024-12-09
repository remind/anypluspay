package com.anypluspay.channelgateway.wxpay;

import com.anypluspay.channelgateway.api.query.QueryGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.GatewayOrder;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.commons.lang.types.Money;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.QueryByOutRefundNoRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/9/20
 */
@Service
@Slf4j
public class WxPayRefundQueryGateway extends AbstractWxPayGateway implements QueryGateway {
    @Override
    public void query(GatewayRequest<GatewayOrder> gatewayRequest, GatewayOrder gatewayOrder, GatewayResult result) {
        WxPayConfig wxPayConfig = getWxPayConfig();
        requestWrapper((Void) -> {
            RefundService refundService = getRefundService(wxPayConfig);
            QueryByOutRefundNoRequest request = new QueryByOutRefundNoRequest();
            request.setOutRefundNo(gatewayOrder.getInstRequestNo());
            request.setSubMchid(wxPayConfig.getMerchantId());
            Refund refund =refundService.queryByOutRefundNo(request);
            result.setRealAmount(new Money(0, refund.getAmount().getRefund().intValue()));
            result.setApiCode(refund.getStatus().name());
        }, result);
        result.setSuccess(true);
    }
}
