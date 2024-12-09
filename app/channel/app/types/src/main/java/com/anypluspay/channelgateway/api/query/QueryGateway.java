package com.anypluspay.channelgateway.api.query;

import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channelgateway.request.GatewayOrder;
import com.anypluspay.channel.types.channel.ChannelApiType;

import java.util.List;

/**
 * 查询网关
 *
 * @author wxj
 * 2024/9/15
 */
public interface QueryGateway extends ChannelGateway<GatewayOrder> {

    List<ChannelApiType> SUPPORT_API_TYPES = List.of(ChannelApiType.SINGLE_QUERY
            , ChannelApiType.SINGLE_REFUND_QUERY);

    @Override
    default GatewayResult call(GatewayRequest<GatewayOrder> gatewayRequest) {
        GatewayResult result = new GatewayResult();
        result.setInstRequestNo(gatewayRequest.getContent().getInstRequestNo());
        query(gatewayRequest, gatewayRequest.getContent(), result);
        return result;
    }

    @Override
    default boolean support(String channelCode, ChannelApiType channelApiType) {
        return SUPPORT_API_TYPES.contains(channelApiType);
    }

    void query(GatewayRequest<GatewayOrder> gatewayRequest, GatewayOrder gatewayOrder, GatewayResult result);
}
