package com.anypluspay.pgw.wallet.response.deposit;

import com.anypluspay.pgw.cashier.request.DepositPayRequest;
import lombok.Data;

/**
 * @author wxj
 * 2025/7/1
 */
@Data
public class DepositResponse {

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 请求参数
     */
    private DepositPayRequest request;
}
