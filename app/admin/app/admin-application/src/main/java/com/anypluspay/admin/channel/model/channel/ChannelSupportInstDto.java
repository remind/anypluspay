package com.anypluspay.admin.channel.model.channel;

import com.anypluspay.component.web.json.std.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/11/28
 */
@Data
public class ChannelSupportInstDto {

    /**
     * 主键
     */
    private Long id;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 目标机构编码
     */
    private String targetInstCode;

    /**
     * 卡类型
     */
    private String cardType;

    /**
     * 卡类型名称
     */
    private String cardTypeName;

    /**
     * 扩展条件
     */
    private String extra;

    /**
     * 单笔限额
     */
    private String perAmountRange;

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
