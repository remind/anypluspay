package com.anypluspay.admin.channel.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 机构请求信息
 * @author wxj
 * 2024/11/7
 */
@Data
public class InstitutionRequest {

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
     * 机构能力
     */
    @NotBlank
    private List<String> instAbility;

    /**
     * 备注
     */
    private String memo;
}
