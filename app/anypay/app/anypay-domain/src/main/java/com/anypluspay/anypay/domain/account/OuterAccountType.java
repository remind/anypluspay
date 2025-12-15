package com.anypluspay.anypay.domain.account;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 外部账户类型
 * @author wxj
 * 2023/12/22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OuterAccountType extends Entity {

    /**
     * 账户类型编码
     */
    private String code;

    /**
     * 账户类型名称
     */
    private String name;

    /**
     * 科目编码
     */
    private String titleCode;

    /**
     * 币种代码
     */
    private String currencyCode;

}
