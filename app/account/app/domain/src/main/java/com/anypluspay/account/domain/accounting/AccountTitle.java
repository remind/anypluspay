package com.anypluspay.account.domain.accounting;

import com.anypluspay.account.types.accounting.AccountTitleRange;
import com.anypluspay.account.types.accounting.AccountTitleStatus;
import com.anypluspay.account.types.accounting.AccountTitleType;
import com.anypluspay.account.types.enums.BalanceDirection;
import com.anypluspay.commons.lang.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2023/12/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountTitle extends Entity {
    /**
     * 科目号
     */
    private String code;
    /**
     * 科目名称
     */
    private String name;
    /**
     * 科目级别，从第1级顺序开始，最多3级，3级才能为叶子节点
     */
    private Integer level;
    /**
     * 父级科目号
     */
    private String parentCode;
    /**
     * 是否叶子节点
     */
    private boolean leaf;
    /**
     * 科目类型
     */
    private AccountTitleType type;
    /**
     * 余额方向
     */
    private BalanceDirection balanceDirection;
    /**
     * 状态
     */
    private AccountTitleStatus status;

    /**
     * 适用范围
     */
    private AccountTitleRange range;
    /**
     * 备注
     */
    private String memo;
}
