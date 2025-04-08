package com.anypluspay.payment.types.asset;

import cn.hutool.json.JSONUtil;
import com.anypluspay.commons.lang.utils.EnumUtil;

/**
 * @author wxj
 * 2024/1/15
 */
public abstract class AssetInfo {

    /**
     * 获取资产类型
     *
     * @return
     */
    public abstract AssetType getAssetType();

    public String toJsonStr() {
        String jsonString = JSONUtil.toJsonStr(this);
        return "{}".equals(jsonString) ? null : jsonString;
    }

    public static AssetInfo parse(String assetTypeCode, String jsonStr) {
        switch (EnumUtil.getByCode(AssetType.class, assetTypeCode)) {
            case BANKCARD:
                return JSONUtil.toBean(jsonStr, BankCardAsset.class);
            case BALANCE:
                return JSONUtil.toBean(jsonStr, BalanceAsset.class);
            default:
                return null;
        }
    }

}
