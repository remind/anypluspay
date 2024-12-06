package com.anypluspay.testbank.domain;

/**
 * @author wxj
 * 2024/11/26
 */
public enum OrderStatus {
    WAIT_BUYER_PAY("待支付"),
    TRADE_CLOSED("超时关闭"),
    TRADE_SUCCESS("交易支付成功");

    private final String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }
}
