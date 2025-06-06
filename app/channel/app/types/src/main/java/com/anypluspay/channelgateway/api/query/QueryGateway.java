package com.anypluspay.channelgateway.api.query;

import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import org.apache.commons.lang3.StringUtils;

/**
 * 查询网关
 *
 * @author wxj
 * 2024/9/15
 */
public interface QueryGateway extends ChannelGateway<QueryModel> {

    @Override
    default GatewayResult call(GatewayRequest<QueryModel> gatewayRequest) throws Exception {
        GatewayResult result = new GatewayResult();
        result.setInstRequestNo(gatewayRequest.getContent().getInstRequestNo());
        query(gatewayRequest, gatewayRequest.getContent(), result);
        if (StringUtils.isBlank(result.getInstRequestNo())) {
            result.setInstRequestNo(gatewayRequest.getContent().getInstRequestNo());
        }
        return result;
    }

    void query(GatewayRequest<QueryModel> gatewayRequest, QueryModel queryModel, GatewayResult result) throws Exception;
}
