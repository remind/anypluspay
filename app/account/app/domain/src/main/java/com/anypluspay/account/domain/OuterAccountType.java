package com.anypluspay.account.domain;

import com.anypluspay.account.types.enums.AccountAttribute;
import com.anypluspay.commons.lang.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

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
     * 外部账户分类
     */
    private AccountAttribute accountAttribute;

    /**
     * 币种代码
     */
    private String currencyCode;

    /**
     * 资金类型
     */
    private List<String> fundTypes = new ArrayList<>();

}
