package com.anypluspay.channelgateway;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channelgateway.param.ApiParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author wxj
 * 2024/12/3
 */
public abstract class AbstractTestBank {

    @Autowired
    protected SysConfig sysConfig;

    @Autowired
    protected ApiParamService apiParamService;

    protected static WebClient webClient = null;

    protected WebClient getWebClient() {
        if (webClient == null) {
            webClient = WebClient.builder().baseUrl(sysConfig.getTestBankUrl()).build();
        }
        return webClient;
    }

    protected TestBankConfig getByParamId(String paramId) {
        TestBankConfig testBankConfig = null;
        String param = apiParamService.getApiParam(paramId);
        if (param != null) {
            testBankConfig = JSONUtil.toBean(param, TestBankConfig.class);
        }
        return testBankConfig;
    }

}
