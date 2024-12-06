package com.anypluspay.channel.domain.repository.channel;

import com.anypluspay.channel.domain.channel.ChannelFullInfo;
import com.anypluspay.channel.types.enums.RequestType;

import java.util.List;

/**
 * @author wxj
 * 2024/8/30
 */
public interface ChannelFullInfoRepository {

    List<ChannelFullInfo> getAllAvailableChannels(RequestType requestType, String payMethod);

    ChannelFullInfo getChannelFullInfo(String channelCode);

}
