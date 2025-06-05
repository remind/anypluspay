package com.anypluspay.channelgateway.test;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channelgateway.api.verify.VerifyModel;
import com.anypluspay.channelgateway.api.verify.VerifySignGateway;
import com.anypluspay.channelgateway.api.verify.VerifySignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.test.request.LocalBankNotifyResult;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/7/15
 */
@Service
public class LocalBankVerifySignGateway extends AbstractLocalBankGateway implements VerifySignGateway {
    @Override
    public void notify(GatewayRequest<VerifyModel> request, VerifyModel verifyModel, VerifySignResult result) {
        result.setSuccess(true);
        LocalBankNotifyResult localBankNotifyResult = JSONUtil.toBean(request.getContent().getRequestBody(), LocalBankNotifyResult.class);
        result.setResponseBody("SUCCESS");
        result.setApiCode(localBankNotifyResult.getResultCode());
        result.setInstRequestNo(localBankNotifyResult.getInstRequestNo());
    }
}
