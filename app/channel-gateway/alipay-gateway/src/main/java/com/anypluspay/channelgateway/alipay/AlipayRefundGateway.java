package com.anypluspay.channelgateway.alipay;

import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.anypluspay.channelgateway.api.refund.RefundContent;
import com.anypluspay.channelgateway.api.refund.RefundGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.commons.response.GlobalResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 退款，一个接口支持所有支付宝订单退款
 *
 * @author wxj
 * 2025/6/5
 */
@Service
public class AlipayRefundGateway extends AbstractAlipayGateway implements RefundGateway {
    @Override
    public void refund(GatewayRequest<RefundContent> gatewayRequest, RefundContent refundOrder, GatewayResult result) throws Exception {
        // 构造请求参数以调用接口
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(refundOrder.getOrigInstRequestNo());
        model.setOutRequestNo(refundOrder.getInstRequestNo());
        model.setRefundAmount(refundOrder.getAmount().toString());
        request.setBizModel(model);
        AlipayTradeRefundResponse response = createAlipayClient(refundOrder.getApiParamId()).execute(request);
        fillResult(response, result, (v) -> {
            result.setInstRequestNo(response.getOutTradeNo());
            result.setInstResponseNo(response.getTradeNo());
            if (StringUtils.isNotBlank(response.getFundChange())) {
                result.setApiCode(response.getFundChange()); // 退款是否成功可以根据同步响应的 fund_change 参数来判断，fund_change 表示本次退款是否发生了资金变化，返回 Y 表示退款成功，返回 N 则表示本次退款未发生资金变动 。
            } else {
                result.setApiCode(GlobalResultCode.UNKNOWN.getCode());
            }
        });
        result.setSuccess(true);
    }
}
