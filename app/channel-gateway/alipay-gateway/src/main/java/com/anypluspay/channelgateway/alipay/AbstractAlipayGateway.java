package com.anypluspay.channelgateway.alipay;

import cn.hutool.json.JSONUtil;
import com.alipay.api.*;
import com.anypluspay.channel.types.result.ProcessResult;
import com.anypluspay.channelgateway.param.ApiParamTemplate;
import com.anypluspay.commons.response.GlobalResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;

/**
 * @author wxj
 * 2024/11/15
 */
@Slf4j
public class AbstractAlipayGateway {

    @Autowired
    private ApiParamTemplate apiParamTemplate;

    protected static String CHARSET = "UTF-8";

    protected static final String RESULT_CODE_SUCCESS  = "10000";

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

    protected void fillResult(AlipayResponse response, ProcessResult result, Consumer<Void> consumer) {
        if (RESULT_CODE_SUCCESS.equals(response.getCode())) {
            consumer.accept(null);
        } else {
            log.error("支付宝处理异常,msg={}", response.getSubMsg());
            result.setApiCode(GlobalResultCode.UNKNOWN.getCode());
        }
    }
}
