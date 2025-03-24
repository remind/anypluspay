package com.anypluspay.admin.channel.model.channel;

import com.anypluspay.component.web.json.std.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/12/3
 */
@Data
public class ChannelMaintainDto {

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
     * 维护时间类型名称
     */
    private String maintainTimeTypeName;

    /**
     * 时间区间
     */
    private String timeRange;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
