package com.anypluspay.account.types.enums;

import com.anypluspay.commons.enums.CodeEnum;

/**
 * 账户分类
 * @author wxj
 * 2023/12/16
 */
public enum AccountFamily  implements CodeEnum {
    INNER("1", "内部账户"),

    OUTER("2", "客户账户");

    /** 代码 */
    private final String    code;
    /** 名称  */
    private final String displayName;

    /**
     * 构造
     * @param code
     * @param displayName
     */
    AccountFamily(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
