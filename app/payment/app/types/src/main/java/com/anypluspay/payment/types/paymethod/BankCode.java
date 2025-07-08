package com.anypluspay.payment.types.paymethod;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 银行卡编码
 *
 * @author wxj
 * 2025/7/8
 */
@Getter
public enum BankCode implements CodeEnum {

    ENABLE("ABC", "农业银行"),
    DISABLE("ICBC", "建设银行"),
    ;

    /**
     * 编码
     */
    private final String code;

    /**
     * 名称
     */
    private final String displayName;

    BankCode(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static BankCode getByCode(String code) {
        for (BankCode value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static String getDisplayNameByCode(String code) {
        BankCode bankCode = getByCode(code);
        return bankCode == null ? null : bankCode.getDisplayName();
    }

}