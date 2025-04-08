package com.anypluspay.channelgateway.api.sign;

import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channelgateway.result.GatewayResult;

/**
 * @author wxj
 * 2024/9/15
 */
public class SignResult extends GatewayResult {

    public void setInstPageUrl(String instPageUrl) {
        addExtra(ChannelExtKey.INST_URL, instPageUrl);
    }
}
