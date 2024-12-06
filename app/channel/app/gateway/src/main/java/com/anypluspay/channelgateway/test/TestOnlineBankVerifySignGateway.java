package com.anypluspay.channelgateway.test;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channelgateway.api.verify.VerifySignGateway;
import com.anypluspay.channelgateway.api.verify.VerifySignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.StringInfo;
import com.anypluspay.channelgateway.test.request.TestOnlineBankNotifyResult;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/7/15
 */
@Service
public class TestOnlineBankVerifySignGateway extends VerifySignGateway {
    @Override
    public void notify(GatewayRequest<StringInfo> request, VerifySignResult result) {
        result.setSuccess(true);
        TestOnlineBankNotifyResult testOnlineBankNotifyResult = JSONUtil.toBean(request.getContent().getRequestBody(), TestOnlineBankNotifyResult.class);
        result.setResponseBody("SUCCESS");
        result.setApiCode(testOnlineBankNotifyResult.getResultCode());
        result.setInstRequestNo(testOnlineBankNotifyResult.getInstRequestNo());
    }
}
