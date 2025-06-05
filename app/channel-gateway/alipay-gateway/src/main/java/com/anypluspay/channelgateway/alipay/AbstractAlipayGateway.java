package com.anypluspay.channelgateway.alipay;

import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.anypluspay.channelgateway.param.ApiParamTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2024/11/15
 */
@Slf4j
public class AbstractAlipayGateway {

    @Autowired
    private ApiParamTemplate apiParamTemplate;

    protected AlipayClient createAlipayClient(String paramId) {
        return apiParamTemplate.load(paramId, "alipayConfig", param -> {
            AlipayParam aliPayParam = JSONUtil.toBean(param, AlipayParam.class);

            AlipayConfig alipayConfig = new AlipayConfig();
            if (aliPayParam.getEnv().equals("sandbox")) {
                alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
            }
            alipayConfig.setFormat("json");
            alipayConfig.setCharset("UTF-8");
            alipayConfig.setAppId(aliPayParam.getAppId());
            alipayConfig.setSignType(aliPayParam.getSignType());
            alipayConfig.setPrivateKey(aliPayParam.getPrivateKey());
            alipayConfig.setAlipayPublicKey(aliPayParam.getAlipayPublicKey());
            AlipayClient alipayClient = null;
            try {
                alipayClient = new DefaultAlipayClient(alipayConfig);
            } catch (AlipayApiException e) {
                log.error("创建支付宝客户端失败", e);
                throw new RuntimeException(e);
            }
            return alipayClient;
        });
    }

    protected AlipayParam getAlipayParam(String paramId) {
        return apiParamTemplate.load(paramId, "alipayParam", param -> JSONUtil.toBean(param, AlipayParam.class));
    }

}
