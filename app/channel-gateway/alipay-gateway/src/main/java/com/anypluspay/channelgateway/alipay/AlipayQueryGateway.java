package com.anypluspay.channelgateway.alipay;

import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.anypluspay.channelgateway.api.query.QueryGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.NormalContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.commons.lang.types.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 支查询，一个接口支持所有支付宝订单的查询
 *
 * @author wxj
 * 2025/6/4
 */
@Service
@Slf4j
public class AlipayQueryGateway extends AbstractAlipayGateway implements QueryGateway {
    @Override
    public void query(GatewayRequest<NormalContent> gatewayRequest, NormalContent normalContent, GatewayResult result) throws Exception {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(normalContent.getInstRequestNo());
        request.setBizModel(model);
        AlipayTradeQueryResponse response = createAlipayClient(normalContent.getApiParamId()).execute(request);
        fillResult(response, result, (v) -> {
            result.setInstRequestNo(response.getOutTradeNo());
            result.setInstResponseNo(response.getTradeNo());
            result.setApiCode(response.getTradeStatus());
            result.setRealAmount(new Money(response.getTotalAmount()));
        });
        result.setSuccess(true);
    }


}
