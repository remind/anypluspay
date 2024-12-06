package com.anypluspay.admin.model.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2024/8/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChannelApiQuery extends PageQuery {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 是否可用
     */
    private Boolean enable;
}
