package com.anypluspay.channelgateway.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2024/11/15
 */
public class AbstractAlipayGateway {

    @Autowired
    private AliPayParam aliPayParam;

    protected AlipayClient build() {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setFormat("json");
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        alipayConfig.setAppId(aliPayParam.getAppId());
        alipayConfig.setPrivateKey(aliPayParam.getPrivateKey());
        alipayConfig.setAlipayPublicKey(aliPayParam.getAlipayPublicKey());
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(alipayConfig);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        return alipayClient;
    }
}
