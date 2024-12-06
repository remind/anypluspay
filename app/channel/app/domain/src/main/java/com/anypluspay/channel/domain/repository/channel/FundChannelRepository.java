package com.anypluspay.channel.domain.repository.channel;

import com.anypluspay.channel.domain.channel.fund.FundChannel;

import java.util.List;

/**
 * @author wxj
 * 2024/7/11
 */
public interface FundChannelRepository {

    List<FundChannel> loadAllEnable();

}
