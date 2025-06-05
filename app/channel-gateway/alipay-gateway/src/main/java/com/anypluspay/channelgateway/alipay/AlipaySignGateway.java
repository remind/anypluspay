package com.anypluspay.channelgateway.alipay;

import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.anypluspay.channelgateway.api.sign.*;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.response.GlobalResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/11/15
 */
@Service
public class AlipaySignGateway extends AbstractAlipayGateway implements SignGateway {
    @Override
    public void sign(GatewayRequest<SignNormalContent> gatewayRequest, SignNormalContent signOrderInfo, SignResult result) throws Exception {
        AlipayTradePagePayRequest req = getAlipayTradeAppPayRequest(signOrderInfo);
        AlipayTradePagePayResponse response = createAlipayClient(signOrderInfo.getApiParamId()).pageExecute(req, "GET");
        result.setRedirectionData(new RedirectionData(RedirectionType.PAGE_URL.getCode(), response.getBody()));
        result.setApiMessage(response.getMsg());
        result.setApiCode(GlobalResultCode.SUCCESS.getCode());
        result.setSuccess(true);

        fillResult(response, result, (v) -> {
            result.setRedirectionData(new RedirectionData(RedirectionType.PAGE_URL.getCode(), response.getBody()));
            result.setApiMessage(response.getMsg());
            result.setApiCode(GlobalResultCode.SUCCESS.getCode());
        });
        result.setSuccess(true);
    }

    private AlipayTradePagePayRequest getAlipayTradeAppPayRequest(SignNormalContent signOrderInfo) {
        AlipayTradePagePayRequest req = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(signOrderInfo.getInstRequestNo());
        model.setSubject(StringUtils.isBlank(signOrderInfo.getGoodsDesc()) ? "订单描述" : signOrderInfo.getGoodsDesc()); //订单标题
        model.setBody(StringUtils.isBlank(signOrderInfo.getGoodsDesc()) ? "订单描述" : signOrderInfo.getGoodsDesc()); //订单描述信息
        model.setTotalAmount(signOrderInfo.getAmount().getAmount().toString());  //支付金额
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        req.setNotifyUrl(signOrderInfo.getServerNotifyUrl()); // 设置异步通知地址
        req.setReturnUrl(signOrderInfo.getReturnPageUrl());
        req.setBizModel(model);
        return req;
    }
}
