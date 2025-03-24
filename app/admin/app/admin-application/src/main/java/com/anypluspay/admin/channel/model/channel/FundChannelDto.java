package com.anypluspay.admin.channel.model.channel;

import com.anypluspay.component.web.json.std.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 资金渠道
 * @author wxj
 * 2024/11/19
 */
@Data
public class FundChannelDto {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属机构代码
     */
    private String instCode;

    /**
     * 请求模式
     */
    private String requestType;

    /**
     * 请求模型名称
     */
    private String requestTypeName;

    /**
     * 支持的支付方式
     */
    private String payMethods;

    /**
     * 状态，是否可用
     */
    private Boolean enable;

    /**
     * 扩展信息
     */
    private String extra;

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
