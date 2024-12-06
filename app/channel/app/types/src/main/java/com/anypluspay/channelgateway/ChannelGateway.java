package com.anypluspay.channelgateway;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.OrderInfo;
import com.anypluspay.channelgateway.request.RequestContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.test.TestFlag;

/**
 * @author wxj
 * 2024/9/13
 */
public interface ChannelGateway<T extends RequestContent> {

    GatewayResult call(GatewayRequest<T > gatewayRequest);

    boolean support(String channelCode, ChannelApiType channelApiType);

    default boolean isTest(OrderInfo orderInfo) {
        return StrUtil.isNotBlank(orderInfo.getExtValue(ExtKey.TEST_FLAG));
    }

    default TestFlag getTestFlag(OrderInfo orderInfo) {
        if (isTest(orderInfo)) {
            return JSONUtil.toBean(orderInfo.getExtValue(ExtKey.TEST_FLAG), TestFlag.class);
        }
        return null;
    }
}