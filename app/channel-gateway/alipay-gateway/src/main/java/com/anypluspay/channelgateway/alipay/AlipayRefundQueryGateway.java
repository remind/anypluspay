package com.anypluspay.channelgateway.alipay;

import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.anypluspay.channelgateway.api.query.QueryGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.NormalContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.commons.lang.types.Money;
import org.springframework.stereotype.Service;

/**
 * 退款查询，对应退款接口的所有支付宝订单
 *
 * @author wxj
 * 2025/6/5
 */
@Service
public class AlipayRefundQueryGateway extends AbstractAlipayGateway implements QueryGateway {
    @Override
    public void query(GatewayRequest<NormalContent> gatewayRequest, NormalContent normalContent, GatewayResult result) throws Exception {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();

        // 设置支付宝交易号
        // TODO 支付宝退款查询需要传入支付单号
//        model.setOutTradeNo(normalContent.get());
        model.setOutRequestNo(normalContent.getInstRequestNo());

        request.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse response = createAlipayClient(normalContent.getApiParamId()).execute(request);
        fillResult(response, result, (v) -> {
            result.setInstRequestNo(response.getOutTradeNo());
            result.setInstResponseNo(response.getTradeNo());
            result.setApiCode(response.getRefundStatus());
            result.setRealAmount(new Money(response.getRefundAmount()));
        });
        result.setSuccess(true);
    }
}
