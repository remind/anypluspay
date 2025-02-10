package com.anypluspay.payment.types.asset;

import lombok.Data;

/**
 * @author wxj
 * 2024/1/21
 */
@Data
public class BankCardAsset extends AssetInfo {

    private String bankCardNo;

    private String bankName;

    /**
     * 银行机构代码
     */
    private String instCode;

    public BankCardAsset() {
    }

    public BankCardAsset(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    @Override
    public AssetType getAssetType() {
        return AssetType.BANKCARD;
    }
}
