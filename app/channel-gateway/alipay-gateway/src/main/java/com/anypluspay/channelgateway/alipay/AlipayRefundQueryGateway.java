package com.anypluspay.channelgateway.alipay;

import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.anypluspay.channelgateway.api.query.QueryGateway;
import com.anypluspay.channelgateway.api.query.QueryModel;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.response.GlobalResultCode;
import org.apache.commons.lang3.StringUtils;
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
    public void query(GatewayRequest<QueryModel> gatewayRequest, QueryModel normalContent, GatewayResult result) throws Exception {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();

        // 需要原支付单号
        model.setOutTradeNo(normalContent.getOrigInstRequestNo());
        model.setOutRequestNo(normalContent.getInstRequestNo());

        request.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse response = createAlipayClient(normalContent.getApiParamId()).execute(request);
        fillResult(response, result, (v) -> {
            result.setInstRequestNo(response.getOutTradeNo());
            result.setInstResponseNo(response.getTradeNo());
            result.setApiCode(StringUtils.isNotBlank(response.getRefundStatus()) ? response.getRefundStatus() : GlobalResultCode.UNKNOWN.getCode());
            result.setRealAmount(StringUtils.isNotBlank(response.getRefundAmount()) ? new Money(response.getRefundAmount()) : null);
        });
        result.setSuccess(true);
    }
}
