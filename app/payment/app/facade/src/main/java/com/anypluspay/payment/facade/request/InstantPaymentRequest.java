package com.anypluspay.payment.facade.request;

import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

import java.util.List;

/**
 * 支付请求
 * @author remind
 * 2023年07月14日 20:28
 */
@Data
public class InstantPaymentRequest extends BasePaymentRequest {

    /**
     * 支付金额
     */
    private Money payAmount;

    /**
     * 付款方ID
     */
    private String payerId;

    /**
     * 付款资金明细
     */
    private List<FundDetailInfo> payerFundDetail;

    /**
     * 收款资金明细
     */
    private List<FundDetailInfo> payeeFundDetail;

    @Override
    public String getMemberId() {
        return this.payerId;
    }
}
