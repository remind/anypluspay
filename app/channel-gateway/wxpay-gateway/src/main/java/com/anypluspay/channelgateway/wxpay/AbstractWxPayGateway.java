package com.anypluspay.channelgateway.wxpay;

import com.anypluspay.channelgateway.result.GatewayResult;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.refund.RefundService;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * @author wxj
 * 2024/9/18
 */
@Slf4j
public abstract class AbstractWxPayGateway {

    void requestWrapper(Consumer<Void> action, GatewayResult result) {
        try {
            action.accept(null);
        } catch (ServiceException e) {
            log.error("微信请求失败", e);
            result.setApiCode(e.getErrorCode());
            result.setApiMessage(e.getErrorMessage());
        }
    }

    protected JsapiServiceExtension getJsapiService(WxPayConfig wxPayConfig) {
        return new JsapiServiceExtension.Builder().config(getConfig(wxPayConfig)).build();
    }

    protected RefundService getRefundService(WxPayConfig wxPayConfig) {
        return new RefundService.Builder().config(getConfig(wxPayConfig)).build();
    }

    protected Config getConfig(WxPayConfig wxPayConfig) {
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(wxPayConfig.getMerchantId())
                .privateKeyFromPath(wxPayConfig.getPrivateKeyFromPath())
                .merchantSerialNumber(wxPayConfig.getMerchantSerialNumber())
                .apiV3Key(wxPayConfig.getApiV3Key())
                .build();
    }

    protected WxPayConfig getWxPayConfig() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId("wxfb22f878a44cac32");
        wxPayConfig.setMerchantId("1666913804");
        wxPayConfig.setPrivateKeyFromPath("/Users/wxj/Downloads/1666913804_20240206_cert/apiclient_key.pem");
        wxPayConfig.setMerchantSerialNumber("5564C0F22316D15DA011FBFA707887B4221A0F03");
        wxPayConfig.setApiV3Key("AAsfyU33PzNo4rFxN3UomIRNgTsYL7gG");
        return wxPayConfig;
    }
}
