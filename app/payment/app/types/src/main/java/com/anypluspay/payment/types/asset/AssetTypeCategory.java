package com.anypluspay.payment.types.asset;

/**
 * 资产类型分类
 * @author remind
 * 2023年05月07日 18:58
 */
public enum AssetTypeCategory {

    EXTERNAL("EXTERNAL", "外部指令"),

    ACCOUNTING("ACCOUNTING", "会计指令");


    private String code;

    private String name;

    AssetTypeCategory(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
