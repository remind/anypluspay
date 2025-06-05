package com.anypluspay.channelgateway.alipay;

import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayConstants;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.anypluspay.channelgateway.api.verify.VerifyModel;
import com.anypluspay.channelgateway.api.verify.VerifySignGateway;
import com.anypluspay.channelgateway.api.verify.VerifySignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.types.CallbackType;
import com.anypluspay.commons.enums.ResultStatusEnum;
import com.anypluspay.commons.lang.types.Money;
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
    public void notify(GatewayRequest<VerifyModel> request, VerifyModel verifyModel, VerifySignResult result) {
        Map<String, String> paramsMap = JSONUtil.toBean(verifyModel.getRequestBody(), Map.class);
        if (CallbackType.PAGE.getCode().equals(verifyModel.getCallbackType())) {
            // 构造请求参数以调用接口
            AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(paramsMap.get("out_trade_no"));
            alipayRequest.setBizModel(model);
            try {
                AlipayTradeQueryResponse response = createAlipayClient(verifyModel.getApiParamId()).execute(alipayRequest);
                result.setInstRequestNo(response.getOutTradeNo());
                result.setInstResponseNo(response.getTradeNo());
                result.setApiCode(response.getTradeStatus());
                result.setRealAmount(new Money(response.getTotalAmount()));
                result.setSuccess(true);
            } catch (Exception e) {
                log.error("支付宝验签异常", e);
                result.setSuccess(false);
                result.setApiCode(ResultStatusEnum.UNKNOWN.getCode());
                result.setApiMessage(e.getMessage());
            }
        } else if (CallbackType.SERVER.getCode().equals(verifyModel.getCallbackType())) {
            try {
                boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, getAlipayParam(verifyModel.getApiParamId()).getAlipayPublicKey(), AlipayConstants.CHARSET, AlipayConstants.SIGN_TYPE); //调用SDK验证签名
                result.setInstRequestNo(paramsMap.get("out_trade_no"));
                if (signVerified) {
                    result.setApiCode(paramsMap.get("trade_status"));
                    result.setRealAmount(new Money(paramsMap.get("total_amount")));
                    result.setSuccess(true);
                } else {
                    result.setSuccess(false);
                    result.setApiCode(ResultStatusEnum.UNKNOWN.getCode());
                    result.setApiMessage("验签不通过");
                }
            } catch (Exception e) {
                log.error("支付宝验签异常", e);
                result.setSuccess(false);
                result.setApiCode(ResultStatusEnum.UNKNOWN.getCode());
                result.setApiMessage(e.getMessage());
            }
        }
    }
}
