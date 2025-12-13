package com.anypluspay.anypay.account;

/**
 * @author wxj
 * 2023/12/16
 */
public interface AccountDomainConstants {

    /**
     * 科目层级最小值
     */
    int ACCOUNT_TITLE_TIER_MIN = 1;

    /**
     * 科目层级最大值
     */
    int ACCOUNT_TITLE_TIER_MAX = 3;

    /**
     * 科目每层级的长度为3，科目规则为 type + 000 + 000 + 000，首位为类型，后面每层级占3位
     */
    int ACCOUNT_TITLE_PER_LEN = 3;

    /**
     * 外部户账户自增最大自增值
     */
    int OUTER_ACCOUNT_NO_MAX_INC = 99999;

    /**
     * 内部户账户自增最大自增值
     */
    int INNER_ACCOUNT_NO_MAX_INC = 9999;

}
