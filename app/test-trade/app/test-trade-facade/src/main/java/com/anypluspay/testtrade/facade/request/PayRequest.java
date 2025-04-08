package com.anypluspay.testtrade.facade.request;

import lombok.Data;

import java.util.List;

/**
 * @author wxj
 * 2025/3/18
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
    private List<PayMethod> payMethods;
}
