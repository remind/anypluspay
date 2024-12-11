package com.anypluspay.admin.model.order;

import com.anypluspay.component.web.json.std.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/11/21
 */
@Data
public class InstProcessOrderDto {

    /**
     * 机构过程订单号
     */
    private String instProcessId;

    /**
     * 机构订单号
     */
    private String instOrderId;

    /**
     * 渠道编码
     */
    private String fundChannelCode;

    /**
     * 接口类型
     */
    private String apiType;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 统一结果码
     */
    private String unityCode;

    /**
     * 统一结果消息
     */
    private String unityMessage;

    /**
     * 渠道API结果码
     */
    private String apiCode;

    /**
     * 渠道API子结果码
     */
    private String apiSubCode;

    /**
     * 渠道API消息
     */
    private String apiMessage;

    /**
     * 扩展信息
     */
    private String extra;

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
