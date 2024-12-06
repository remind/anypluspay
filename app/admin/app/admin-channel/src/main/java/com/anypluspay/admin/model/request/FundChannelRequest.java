package com.anypluspay.admin.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 资金渠道请求对象
 * @author wxj
 * 2024/11/12
 */
@Data
public class FundChannelRequest {

    /**
     * 渠道编码
     */
    @NotNull(message = "渠道编码不能为空")
    @Length(min = 3, max = 16, message = "渠道编码长度为3-16位")
    private String code;

    /**
     * 所属机构代码
     */
    @NotNull(message = "所属机构代码不能为空")
    @Length(min = 3, max = 16, message = "所属机构代码长度为3-16位")
    private String instCode;

    /**
     * 渠道名称
     */
    @NotNull(message = "渠道名称不能为空")
    @Length(min = 3, max = 16, message = "渠道名称长度为3-16位")
    private String name;

    /**
     * 请求类型
     */
    @NotNull(message = "请求类型不能为空")
    private String requestType;

    /**
     * 支付方式
     */
    @NotNull(message = "支付方式不能为空")
    private String payMethods;

    /**
     * 是否可用
     */
    private boolean enable;

    /**
     * 扩展信息
     */
    @Length(max = 256, message = "扩展信息长度不能超过256位")
    private String extra;

    /**
     * 描述
     */
    @Length(max = 256, message = "描述长度不能超过256位")
    private String memo;

}
