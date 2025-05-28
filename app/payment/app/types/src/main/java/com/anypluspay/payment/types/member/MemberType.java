package com.anypluspay.payment.types.member;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 会员类型
 * @author wxj
 * 2025/5/27
 */
@Getter
public enum MemberType implements CodeEnum {

    PERSONAL("1", "10", "个人"),
    COMPANY("2", "20", "企业");

    /**
     * 代码，只能为一位正整数
     */
    private final String code;

    /**
     * ID前缀,2位数字
     */
    private final String prefix;

    /**
     * 显示名称
     */
    private final String displayName;

    MemberType(String code, String prefix, String displayName) {
        this.code = code;
        this.prefix = prefix;
        this.displayName = displayName;
    }

    /**
     * 通过代码获取枚举项
     *
     * @param code
     * @return
     */
    public static MemberType getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (MemberType memberType : MemberType.values()) {
            if (memberType.getCode().equals(code)) {
                return memberType;
            }
        }

        return null;
    }
}
