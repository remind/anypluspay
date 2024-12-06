package com.anypluspay.channel.types.manger.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * @author wxj
 * 2024/8/25
 */
@Data
public class FundChannelQuery extends PageQuery {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 是否可用
     */
    private Boolean enable;

}