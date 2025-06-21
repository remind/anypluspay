package com.anypluspay.channelgateway.alipay;

import cn.hutool.json.JSONUtil;
import com.alipay.api.internal.util.AlipaySignature;
import com.anypluspay.channelgateway.api.verify.VerifyModel;
import com.anypluspay.channelgateway.api.verify.VerifySignGateway;
import com.anypluspay.channelgateway.api.verify.VerifySignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.response.GlobalResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 通知验签
 *
 * @author wxj
 * 2025/6/4
 */
@Service
@Slf4j
public class AlipayVerifySignGateway extends AbstractAlipayGateway implements VerifySignGateway {
    @Override
    public void notify(GatewayRequest<VerifyModel> request, VerifyModel verifyModel, VerifySignResult result) throws Exception {
        Map<String, String> paramsMap = JSONUtil.toBean(verifyModel.getRequestBody(), Map.class);
        AlipayParam alipayParam = getAlipayParam(verifyModel.getApiParamId());
        // 后端回调直接验签
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, alipayParam.getAlipayPublicKey(), CHARSET, alipayParam.getSignType()); //调用SDK验证签名
        result.setInstRequestNo(paramsMap.get("out_trade_no"));
        if (signVerified) {
            result.setApiCode(paramsMap.get("trade_status"));
            result.setRealAmount(new Money(paramsMap.get("total_amount")));
        } else {
            result.setApiCode(GlobalResultCode.UNKNOWN.getCode());
            result.setApiMessage("验签不通过");
        }
        result.setSuccess(true);
    }
}
