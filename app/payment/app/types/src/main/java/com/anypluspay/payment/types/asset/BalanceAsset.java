package com.anypluspay.payment.types.asset;

import lombok.Data;

/**
 * @author wxj
 * 2024/1/15
 */
@Data
public class BalanceAsset extends AssetInfo {

    /**
     * 用户ID
     */
    private String memberId;

    /**
     * 账户ID
     */
    private String accountNo;

    public BalanceAsset() {}
    public BalanceAsset(String memberId, String accountNo) {
        this.memberId = memberId;
        this.accountNo = accountNo;
    }

    @Override
    public AssetType getAssetType() {
        return AssetType.BALANCE;
    }
}
