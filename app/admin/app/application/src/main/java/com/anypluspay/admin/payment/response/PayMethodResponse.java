package com.anypluspay.admin.payment.response;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/5/28
 */
@Data
public class PayMethodResponse {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 支付模式
     */
    private String payModel;

    /**
     * 支付模式名称
     */
    private String payModelName;

    /**
     * 资产类型
     */
    private String assetType;

    /**
     * 资产类型名称
     */
    private String assetTypeName;

    /**
     * 支付机构
     */
    private String payInst;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 扩展信息
     */
    private String extension;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
