package com.anypluspay.admin.demo.cashier.request;

import lombok.Data;

import java.util.List;

/**
 * @author wxj
 * 2025/5/19
 */
@Data
public class PayRequest {
    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 支付方式
     */
    private List<String> payMethods;
}
