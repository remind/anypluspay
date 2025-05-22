package com.anypluspay.admin.channel.model.channel;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/12/1
 */
@Data
public class ApiResultCodeDto {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 接口类型
     */
    private String apiType;

    /**
     * 接口类型名称
     */
    private String apiTypeName;

    /**
     * 机构API结果码
     */
    private String instApiCode;

    /**
     * 机构API子结果码
     */
    private String instApiSubCode;

    /**
     * 机构API消息
     */
    private String instApiMessage;

    /**
     * 统一结果码
     */
    private String unityCode;

    /**
     * 机构订单结果状态
     */
    private String resultStatus;

    /**
     * 机构订单结果状态名称
     */
    private String resultStatusName;

    /**
     * 是否映射
     */
    private Boolean useMapping;

    /**
     * 是否重路由
     */
    private Boolean reRouteEnable;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
