package com.anypluspay.payment.facade.acquiring.pay;

import com.anypluspay.payment.facade.request.FundDetailInfo;
import lombok.Data;

import java.util.List;

/**
 * 交易支付请求
 * @author wxj
 * 2025/5/17
 */
@Data
public class AcquiringPayRequest {

    /**
     * 合作方ID
     */
    private String partnerId;

    /**
     * 外部交易号
     */
    private String outTradeNo;

    /**
     * 支付单ID
     */
    private String paymentId;

    /**
     * 付款资金明细
     */
    private List<FundDetailInfo> payerFundDetail;

}
