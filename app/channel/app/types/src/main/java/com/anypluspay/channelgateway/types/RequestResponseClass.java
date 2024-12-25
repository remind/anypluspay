package com.anypluspay.channelgateway.types;

import com.anypluspay.channelgateway.api.refund.RefundContent;
import com.anypluspay.channelgateway.api.sign.SignNormalContent;
import com.anypluspay.channelgateway.api.sign.SignResult;
import com.anypluspay.channelgateway.api.verify.VerifySignResult;
import com.anypluspay.channelgateway.request.FundOutContent;
import com.anypluspay.channelgateway.request.NormalContent;
import com.anypluspay.channelgateway.request.RequestContent;
import com.anypluspay.channelgateway.request.StringContent;
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

    SIGN(ChannelApiType.SIGN, SignNormalContent.class, SignResult.class),
    SINGLE_REFUND(ChannelApiType.SINGLE_REFUND, RefundContent.class, null),
    SINGLE_FUND_OUT(ChannelApiType.SINGLE_FUND_OUT, FundOutContent.class, null),
    VERIFY_SIGN(ChannelApiType.VERIFY_SIGN, StringContent.class, VerifySignResult.class),
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
        return requestResponseClass == null || requestResponseClass.getRequestClass() == null ? NormalContent.class : requestResponseClass.getRequestClass();
    }

    public static Class<? extends ProcessResult> getResponseClass(ChannelApiType channelApiType) {
        RequestResponseClass requestResponseClass = getByChannelApiType(channelApiType);
        return requestResponseClass == null || requestResponseClass.getResultClass() == null ? GatewayResult.class : requestResponseClass.getResultClass();
    }
}
