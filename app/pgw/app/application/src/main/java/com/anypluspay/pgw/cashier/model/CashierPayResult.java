package com.anypluspay.pgw.cashier.model;

import lombok.Data;

/**
 * @author wxj
 * 2025/6/4
 */
@Data
public class CashierPayResult {

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 机构跳转参数
     */
    private String ird;

    /**
     * 结果码
     */
    protected String resultCode;

    /**
     * 结果信息
     */
    protected String resultMsg;
}
