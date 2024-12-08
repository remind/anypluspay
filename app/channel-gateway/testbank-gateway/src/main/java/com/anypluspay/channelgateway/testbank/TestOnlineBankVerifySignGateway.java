package com.anypluspay.channelgateway.testbank;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.api.verify.VerifySignGateway;
import com.anypluspay.channelgateway.api.verify.VerifySignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.StringInfo;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author wxj
 * 2024/12/8
 */
@DubboService(group = "test-bank-vs", interfaceClass = ChannelGateway.class)
public class TestOnlineBankVerifySignGateway extends VerifySignGateway {

    @Override
    public void notify(GatewayRequest<StringInfo> request, VerifySignResult result) {
        result.setSuccess(true);
        JSONObject jsonObject =  JSONUtil.parseObj(request.getContent().getRequestBody());
        result.setResponseBody("SUCCESS");
        result.setInstRequestNo(jsonObject.getStr("outTradeNo"));
        result.setApiCode(jsonObject.getStr("status"));
    }
}
