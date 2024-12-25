package com.anypluspay.channelgateway.testbank;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.anypluspay.channelgateway.api.verify.VerifySignGateway;
import com.anypluspay.channelgateway.api.verify.VerifySignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.StringContent;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/12/8
 */
@Service
public class TestOnlineBankVerifySignGateway implements VerifySignGateway {
    @Override
    public void notify(GatewayRequest<StringContent> request, VerifySignResult result) {
        result.setSuccess(true);
        JSONObject jsonObject =  JSONUtil.parseObj(request.getContent().getRequestBody());
        result.setResponseBody("SUCCESS");
        result.setInstRequestNo(jsonObject.getStr("outTradeNo"));
        result.setInstResponseNo(jsonObject.getStr("payOrderId"));
        result.setApiCode(jsonObject.getStr("status"));
    }
}
