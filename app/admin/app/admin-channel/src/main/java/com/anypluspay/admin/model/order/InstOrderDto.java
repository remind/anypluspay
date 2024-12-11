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
public class InstOrderDto {

    /**
     * 机构订单号
     */
    private String instOrderId;

    /**
     * 业务订单号
     */
    private String bizOrderId;

    /**
     * 处理时间类型
     */
    private String processTimeType;

    /**
     * 机构请求单号
     */
    private String instRequestNo;

    /**
     * 机构响应单号
     */
    private String instResponseNo;

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
     * 请求扩展信息
     */
    private String requestExtra;

    /**
     * 响应扩展信息
     */
    private String responseExtra;

    /**
     * 处理时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime processTime;

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
