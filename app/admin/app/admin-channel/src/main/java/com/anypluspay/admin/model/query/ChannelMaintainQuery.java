package com.anypluspay.admin.model.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author wxj
 * 2024/8/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChannelMaintainQuery extends PageQuery {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 新增时间起始
     */
    private LocalDate gmtStart;

    /**
     * 新增时间结束
     */
    private LocalDate gmtEnd;
}
