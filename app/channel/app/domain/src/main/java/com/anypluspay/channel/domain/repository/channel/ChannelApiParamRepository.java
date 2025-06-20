package com.anypluspay.channel.domain.repository.channel;

import com.anypluspay.channel.domain.channel.api.ChannelApiParam;

import java.util.List;

/**
 * @author wxj
 * 2025/6/5
 */
public interface ChannelApiParamRepository {

    List<ChannelApiParam> loadAllEnable();

    ChannelApiParam loadByPartnerIdAndChannelCode(String partnerId, String channelCode);

    ChannelApiParam load(String id);
}
