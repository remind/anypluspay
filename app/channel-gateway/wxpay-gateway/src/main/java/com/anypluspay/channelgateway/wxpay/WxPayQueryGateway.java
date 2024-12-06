package com.anypluspay.channelgateway.wxpay;

import com.anypluspay.channelgateway.api.query.QueryGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.OrderInfo;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.commons.lang.types.Money;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.QueryOrderByOutTradeNoRequest;
import com.wechat.pay.java.service.payments.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/9/18
 */
@Service
@Slf4j
public class WxPayQueryGateway extends AbstractWxPayGateway implements QueryGateway {

    @Override
    public void query(GatewayRequest<OrderInfo> gatewayRequest, OrderInfo orderInfo, GatewayResult result) {
        WxPayConfig wxPayConfig = getWxPayConfig();
        try {
            JsapiServiceExtension service = getJsapiService(wxPayConfig);
            QueryOrderByOutTradeNoRequest queryRequest = new QueryOrderByOutTradeNoRequest();
            queryRequest.setOutTradeNo(orderInfo.getInstRequestNo());
            queryRequest.setMchid(wxPayConfig.getMerchantId());
            Transaction transaction = service.queryOrderByOutTradeNo(queryRequest);
            result.setApiCode(transaction.getTradeState().name());
            result.setRealAmount(new Money(0, transaction.getAmount().getTotal()));
        } catch (ServiceException e) {
            log.error("微信请求失败", e);
            result.setApiCode(e.getErrorCode());
            result.setApiMessage(e.getErrorMessage());
        }
        result.setSuccess(true);
    }
}
