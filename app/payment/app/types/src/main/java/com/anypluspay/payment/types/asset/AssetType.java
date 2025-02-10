package com.anypluspay.payment.types.asset;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 资产类型
 * @author wxj
 * 2024/1/15
 */
@Getter
public enum AssetType implements CodeEnum {

    BANKCARD("BANKCARD", AssetTypeCategory.EXTERNAL, "银行卡"),
    BALANCE("BALANCE", AssetTypeCategory.ACCOUNTING, "余额"),
    WX("WX", AssetTypeCategory.EXTERNAL, "微信");

    private final String code;

    private final AssetTypeCategory assetTypeCategory;

    private final String displayName;

    AssetType(String code, AssetTypeCategory assetTypeCategory, String displayName) {
        this.code = code;
        this.assetTypeCategory = assetTypeCategory;
        this.displayName = displayName;
    }
}
