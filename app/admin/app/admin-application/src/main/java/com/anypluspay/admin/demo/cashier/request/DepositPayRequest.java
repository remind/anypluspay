package com.anypluspay.admin.demo.cashier.request;

import com.anypluspay.testtrade.facade.request.PayMethod;
import lombok.Data;

import java.util.List;

/**
 * @author wxj
 * 2025/5/15
 */
@Data
public class DepositPayRequest extends DepositCashierRequest {

    /**
     * 支付方式
     */
    private List<PayMethod> payMethods;
}
