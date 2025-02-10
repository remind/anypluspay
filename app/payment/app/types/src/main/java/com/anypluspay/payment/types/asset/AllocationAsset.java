package com.anypluspay.payment.types.asset;

import lombok.Data;

/**
 * @author remind
 * 2023年05月06日 22:16
 */
@Data
public class AllocationAsset {
    /**
     * 详情ID
     */
    private String detailId;

    /**
     * 分配金额
     */
    private String amount;
}
