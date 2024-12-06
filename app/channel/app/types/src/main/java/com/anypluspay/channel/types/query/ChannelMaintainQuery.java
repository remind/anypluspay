package com.anypluspay.channel.types.manger.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * @author wxj
 * 2024/8/30
 */
@Data
public class ChannelMaintainQuery extends PageQuery {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 是否可用
     */
    private Boolean enable;
}
