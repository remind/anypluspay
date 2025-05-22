package com.anypluspay.admin.account.model.dto;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/12/26
 */
@Data
public class AccountTitleDto {

    /**
     * 科目代码
     */
    private String code;

    /**
     * 科目名称
     */
    private String name;

    /**
     * 科目级别
     */
    private Short tier;

    /**
     * 父科目代码
     */
    private String parentCode;

    /**
     * 是否为叶子节点
     */
    private Boolean leaf;

    /**
     * 类型：1（资产类）；2（负债类）；3(所有者权益)；4（共同类）5(损益类)
     */
    private String type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 余额方向：1:借 2:贷
     */
    private String balanceDirection;

    /**
     * 余额方向名称
     */
    private String balanceDirectionName;

    /**
     * 是否有效
     */
    private Boolean enable;

    /**
     * 适用范围：1.内部科目;2,外部科目
     */
    private String scope;

    /**
     * 适用范围名称
     */
    private String scopeName;

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
