package com.anypluspay.channel.domain.result.service;

import com.anypluspay.channel.domain.result.UnityResult;
import com.anypluspay.channel.types.channel.ChannelApiType;

/**
 * @author wxj
 * 2024/7/10
 */
public interface ApiResultService {

    UnityResult doMatch(String channelCode, ChannelApiType apiType, String instApiCode, String instApiSubCode, String instApiMessage);

}
