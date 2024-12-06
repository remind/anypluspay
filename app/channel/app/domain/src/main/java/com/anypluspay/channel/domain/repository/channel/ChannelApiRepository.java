package com.anypluspay.channel.domain.repository.channel;

import com.anypluspay.channel.domain.channel.api.ChannelApi;

import java.util.List;

/**
 * @author wxj
 * 2024/8/28
 */
public interface ChannelApiRepository {

    /**
     * 获取所有可用的api
     * @return
     */
    List<ChannelApi> loadAllEnable();

}
