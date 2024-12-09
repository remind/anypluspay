package com.anypluspay.channelgateway.wxpay;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.channelgateway.api.sign.SignGateway;
import com.anypluspay.channelgateway.api.sign.SignGatewayOrder;
import com.anypluspay.channelgateway.api.sign.SignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/9/12
 */
@Service
@Slf4j
public class WxPaySignGateway extends AbstractWxPayGateway implements SignGateway {

    @Override
    public void sign(GatewayRequest<SignGatewayOrder> gatewayRequest, SignGatewayOrder signOrderInfo, SignResult result) {
        WxPayConfig wxPayConfig = getWxPayConfig();
        requestWrapper((Void) -> {
            JsapiServiceExtension service = getJsapiService(wxPayConfig);
            PrepayRequest prepayRequest = buildPrepayRequest(signOrderInfo, wxPayConfig);
            PrepayWithRequestPaymentResponse response = service.prepayWithRequestPayment(prepayRequest);
            result.addExtra("timeStamp", response.getTimeStamp());
            result.addExtra("nonceStr", response.getNonceStr());
            result.addExtra("package", response.getPackageVal());
            result.addExtra("signType", response.getSignType());
            result.addExtra("paySign", response.getPaySign());
            result.setSuccess(true);
            result.setApiCode("200");
        }, result);
    }

    private PrepayRequest buildPrepayRequest(SignGatewayOrder signOrderInfo, WxPayConfig wxPayConfig) {
        PrepayRequest request = new PrepayRequest();
        request.setAppid(wxPayConfig.getAppId());
        request.setMchid(wxPayConfig.getMerchantId());
        request.setDescription(buildDes(signOrderInfo.getGoodsDesc()));
        request.setNotifyUrl(signOrderInfo.getServerNotifyUrl());
        request.setOutTradeNo(signOrderInfo.getInstRequestNo());
        Amount amount = new Amount();
        amount.setTotal((int) signOrderInfo.getAmount().getCent());
        request.setAmount(amount);
        Payer payer = new Payer();
        payer.setOpenid(signOrderInfo.getExtValue("openId"));
        request.setPayer(payer);
        return request;
    }

    private String buildDes(String goodsDes) {
        if (StrUtil.isBlank(goodsDes) || goodsDes.length() <= 40) {
            return goodsDes;
        }
        return goodsDes.substring(0, 40) + "...";
    }
}
