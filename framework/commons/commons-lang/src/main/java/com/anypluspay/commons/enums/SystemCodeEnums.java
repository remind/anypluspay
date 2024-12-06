package com.anypluspay.commons.enums;

/**
 * 系统编码枚举
 *
 * @author wxj
 * 2023/11/28
 */
public enum SystemCodeEnums {
    PAYMENT("001", "PAYMENT", "支付核心"),
    BASIC("002", "BASIC", "公共平台"),
    TRADE("003", "TRADE", "交易"),
    CHANNEL("004", "CHANNEL", "渠道"),
    ACCOUNT("004", "ACCOUNT", "账务"),

    ;

    private final String code;

    private final String englishName;

    private final String chineseName;

    SystemCodeEnums(String code, String englishName, String chineseName) {
        this.code = code;
        this.englishName = englishName;
        this.chineseName = chineseName;
    }

    /**
     * 根据代码获取枚举
     *
     * @param code
     * @return SystemCodeEnums
     */
    public static SystemCodeEnums getByCode(String code) {
        for (SystemCodeEnums enumObject : SystemCodeEnums.values()) {
            if (enumObject.getCode().equals(code)) {
                return enumObject;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getChineseName() {
        return chineseName;
    }
}
