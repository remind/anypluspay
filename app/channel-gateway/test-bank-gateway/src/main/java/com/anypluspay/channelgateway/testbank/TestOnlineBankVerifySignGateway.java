package com.anypluspay.channelgateway.testbank;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channelgateway.api.verify.VerifyModel;
import com.anypluspay.channelgateway.api.verify.VerifySignGateway;
import com.anypluspay.channelgateway.api.verify.VerifySignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/12/8
 */
@Service
public class TestOnlineBankVerifySignGateway implements VerifySignGateway {
    @Override
    public void notify(GatewayRequest<VerifyModel> request, VerifyModel verifyModel, VerifySignResult result) {
        result.setSuccess(true);
        JSONObject jsonObject = JSONUtil.parseObj(request.getContent().getRequestBody());
        result.setResponseBody("SUCCESS");
        if (request.getChannelApiType() == ChannelApiType.VERIFY_SIGN) {
            result.setInstRequestNo(jsonObject.getStr("outTradeNo"));
            result.setInstResponseNo(jsonObject.getStr("payOrderId"));
        } else if (request.getChannelApiType() == ChannelApiType.REFUND_VERIFY_SIGN) {
            result.setInstRequestNo(jsonObject.getStr("outRequestNo"));
            result.setInstResponseNo(jsonObject.getStr("refundOrderId"));
        }
        result.setApiCode(jsonObject.getStr("status"));
    }
}
