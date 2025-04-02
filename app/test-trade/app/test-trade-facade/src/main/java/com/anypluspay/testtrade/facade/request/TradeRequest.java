package com.anypluspay.testtrade.facade.request;

import lombok.Data;

/**
 * 交易信息
 * @author wxj
 * 2025/3/18
 */
@Data
public class TradeRequest {

    /**
     * 商户号
     */
    private String merchantId;

    /**
     * 交易标题
     */
    private String subject;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 交易金额
     */
    private String amount;

    /**
     * 付款人ID
     */
    private String payerId;

    /**
     * 收款人ID
     */
    private String payeeId;
}
