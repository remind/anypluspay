package com.anypluspay.channelgateway.types;

import com.anypluspay.channelgateway.api.refund.RefundOrder;
import com.anypluspay.channelgateway.api.sign.SignOrderInfo;
import com.anypluspay.channelgateway.api.sign.SignResult;
import com.anypluspay.channelgateway.request.OrderInfo;
import com.anypluspay.channelgateway.request.RequestContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.result.ProcessResult;
import lombok.Getter;

/**
 * 配置请求和响应对象，如果没有就使用默认的
 *
 * @author wxj
 * 2024/7/5
 */
@Getter
public enum RequestResponseClass {

    SIGN(ChannelApiType.SIGN, SignOrderInfo.class, SignResult.class),
    SINGLE_REFUND(ChannelApiType.SINGLE_REFUND, RefundOrder.class, null),
    ;

    private final ChannelApiType channelApiType;

    private final Class<? extends RequestContent> requestClass;

    private final Class<? extends ProcessResult> resultClass;

    RequestResponseClass(ChannelApiType channelApiType, Class<? extends RequestContent> requestClass, Class<? extends ProcessResult> resultClass) {
        this.channelApiType = channelApiType;
        this.requestClass = requestClass;
        this.resultClass = resultClass;
    }

    public static RequestResponseClass getByChannelApiType(ChannelApiType channelApiType) {
        for (RequestResponseClass requestResponseClass : RequestResponseClass.values()) {
            if (requestResponseClass.getChannelApiType() == channelApiType) {
                return requestResponseClass;
            }
        }
        return null;
    }

    public static Class<? extends RequestContent> getRequestClass(ChannelApiType channelApiType) {
        RequestResponseClass requestResponseClass = getByChannelApiType(channelApiType);
        return requestResponseClass == null ? OrderInfo.class : requestResponseClass.getRequestClass();
    }

    public static Class<? extends ProcessResult> getResponseClass(ChannelApiType channelApiType) {
        RequestResponseClass requestResponseClass = getByChannelApiType(channelApiType);
        return requestResponseClass == null ? GatewayResult.class : requestResponseClass.getResultClass();
    }
}
