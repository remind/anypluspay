package com.anypluspay.payment.types.funds;

import com.anypluspay.payment.types.asset.AllocationAsset;
import lombok.Data;

import java.util.List;

/**
 * @author wxj
 * 2024/1/15
 */
@Data
public class FundRelation {

    /**
     * 总单号
     */
    private String paymentId;

    /**
     * 关系ID
     */
    private String relationId;

    /**
     * 业务单号
     */
    private String bizNo;

    /**
     * 收款方ID
     */
    private String payeeId;

    /**
     * 收款方分配资产
     */
    private List<AllocationAsset> payeeAsset;

    /**
     * 付款方分配资产
     */
    private List<AllocationAsset> payerAsset;
}
