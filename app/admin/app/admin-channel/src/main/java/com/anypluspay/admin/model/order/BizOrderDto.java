package com.anypluspay.admin.model.order;

import com.anypluspay.admin.web.json.RequestTypeSerializer;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.component.web.json.std.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/11/21
 */
@Data
public class BizOrderDto {

    /**
     * 渠道订单号
     */
    private String orderId;

    /**
     * 请求号
     */
    private String requestId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 请求类型
     */
    @JsonSerialize(using = RequestTypeSerializer.class)
    private RequestType requestType;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 机构订单ID
     */
    private String instOrderId;

    /**
     * 扩展信息
     */
    private String extra;

    /**
     * 机构扩展信息
     */
    private String instExtra;

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
