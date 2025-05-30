package com.anypluspay.admin.payment.request;

import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author wxj
 * 2025/5/28
 */
@Data
public class PayMethodRequest {

    /**
     * 编码
     */
    @NotBlank
    @Length(max = 16, message = "编码长度不能超过16位", groups = {UpdateValidate.class, AddValidate.class})
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Length(max = 16, message = "名称长度不能超过16位", groups = {UpdateValidate.class, AddValidate.class})
    private String name;

    /**
     * 支付模式
     */
    @NotBlank
    private String payModel;

    /**
     * 资产类型
     */
    @NotBlank
    private String assetType;

    /**
     * 支付机构
     */
    @NotBlank
    private String payInst;

    /**
     * 状态
     */
    @NotBlank
    private String status;

    /**
     * 扩展信息
     */
    @Length(max = 1024, message = "扩展信息长度不能超过1024位", groups = {UpdateValidate.class, AddValidate.class})
    private String extension;

    /**
     * 备注
     */
    @Length(max = 1024, message = "备注长度不能超过1024位", groups = {UpdateValidate.class, AddValidate.class})
    private String memo;
}
