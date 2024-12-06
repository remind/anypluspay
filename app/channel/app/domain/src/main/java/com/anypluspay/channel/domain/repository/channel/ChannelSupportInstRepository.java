package com.anypluspay.channel.domain.repository.channel;

import com.anypluspay.channel.domain.channel.ChannelSupportInst;

import java.util.List;

/**
 * @author wxj
 * 2024/8/28
 */
public interface ChannelSupportInstRepository {

    List<ChannelSupportInst> loadAll();

}
