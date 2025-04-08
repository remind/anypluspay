package com.anypluspay.channelgateway.api.verify;

import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channel.types.ChannelExtKey;
import lombok.Data;

/**
 * @author wxj
 * 2024/8/19
 */
@Data
public class VerifySignResult extends GatewayResult {

    /**
     * 响应的报文
     */
    public void setResponseBody(String responseBody) {
        this.addExtra(ChannelExtKey.NOTIFY_RESPONSE_DATA, responseBody);
    }
}
