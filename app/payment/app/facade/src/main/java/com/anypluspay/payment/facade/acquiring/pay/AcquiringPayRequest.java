package com.anypluspay.payment.facade.acquiring.pay;

import com.anypluspay.payment.facade.request.FundDetailInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 6, max = 64, message = "合作方长度为6-64")
    private String partnerId;

    /**
     * 外部交易号
     */
    @Length(min = 6, max = 64, message = "外部订单号长度为6-64")
    private String outTradeNo;

    /**
     * 交易单号
     */
    @Length(min = 6, max = 32, message = "交易单号长度为6-32")
    private String tradeId;

    /**
     * 付款资金明细
     */
    @NotNull(message = "付款资金明细不能为空")
    @Valid
    private List<FundDetailInfo> payerFundDetail;

}
