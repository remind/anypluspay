package com.anypluspay.channelgateway.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.anypluspay.channelgateway.api.sign.SignGateway;
import com.anypluspay.channelgateway.api.sign.SignGatewayOrder;
import com.anypluspay.channelgateway.api.sign.SignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/11/15
 */
@Service
public class AlipaySignGateway extends AbstractAlipayGateway implements SignGateway {
    @Override
    public void sign(GatewayRequest<SignGatewayOrder> gatewayRequest, SignGatewayOrder signOrderInfo, SignResult result) {
        AlipayTradeAppPayRequest req = getAlipayTradeAppPayRequest(signOrderInfo);
        try {
            AlipayResponse response = build().sdkExecute(req);
            result.setApiCode(response.getCode());
            result.setApiSubCode(response.getSubCode());
            result.setApiMessage(response.getMsg());
            result.setInstPageUrl(response.getBody());
        } catch (AlipayApiException e) {
            result.setApiCode(e.getErrCode());
            result.setApiMessage(e.getErrMsg());
        }
    }

    @NotNull
    private AlipayTradeAppPayRequest getAlipayTradeAppPayRequest(SignGatewayOrder signOrderInfo) {
        AlipayTradeAppPayRequest req = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(signOrderInfo.getInstRequestNo());
        model.setSubject(signOrderInfo.getGoodsDesc()); //订单标题
        model.setBody(signOrderInfo.getGoodsDesc()); //订单描述信息
        model.setTotalAmount(signOrderInfo.getAmount().getAmount().toString());  //支付金额
        req.setNotifyUrl(signOrderInfo.getServerNotifyUrl()); // 设置异步通知地址
        req.setBizModel(model);
        return req;
    }
}
