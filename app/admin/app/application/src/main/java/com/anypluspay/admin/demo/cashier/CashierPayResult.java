package com.anypluspay.admin.demo.cashier;

import lombok.Data;

/**
 * @author wxj
 * 2025/6/4
 */
@Data
public class CashierPayResult {

    /**
     * 支付单号
     */
    private String paymentId;

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
