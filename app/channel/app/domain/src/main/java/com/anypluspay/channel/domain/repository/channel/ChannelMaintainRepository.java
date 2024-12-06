package com.anypluspay.channel.domain.repository.channel;

import com.anypluspay.channel.domain.channel.ChannelMaintain;
import com.anypluspay.channel.types.manger.query.ChannelMaintainQuery;
import com.anypluspay.commons.response.page.PageResult;

import java.util.List;

/**
 * @author wxj
 * 2024/8/30
 */
public interface ChannelMaintainRepository {

    List<ChannelMaintain> loadAll();

}
