package com.anypluspay.admin.channel.model.request;

import lombok.Data;

/**
 * @author wxj
 * 2024/12/3
 */
@Data
public class ChannelMaintainRequest {

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 维护时间类型
     */
    private String maintainTimeType;

    /**
     * 时间区间
     */
    private String timeRange;

    /**
     * 备注
     */
    private String memo;

}
