package com.anypluspay.admin.channel.model.config;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wxj
 * 2024/12/5
 */
@Data
public class InstitutionDto {
    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 机构能力
     */
    private List<String> instAbility;

    /**
     * 机构能力名称
     */
    private String instAbilityName;

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
     * 最后修改时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
