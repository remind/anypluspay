package com.anypluspay.admin.channel.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 支付方式请求
 * @author wxj
 * 2024/11/12
 */
@Data
public class PayMethodRequest {

    /**
     * 编码
     */
    @NotBlank
    private String code;

    /**
     * 名称
     */
    @NotBlank
    private String name;

    /**
     * 备注
     */
    private String memo;
}
