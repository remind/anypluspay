package com.anypluspay.payment.types.asset;

/**
 * @author wxj
 * 2025/6/3
 */
public class DefaultAsset extends AssetInfo{
    @Override
    public AssetType getAssetType() {
        return AssetType.DEFAULT;
    }
}
