package com.anypluspay.channel.domain.channel;

import com.anypluspay.channel.types.channel.MaintainTimeType;
import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 渠道维护信息
 *
 * @author wxj
 * 2024/6/27
 */
@Data
public class ChannelMaintain extends Entity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 维护时间类型
     */
    private MaintainTimeType maintainTimeType;

    /**
     * 时间区间
     */
    private String timeRange;

    /**
     * 备注
     */
    private String memo;

}
