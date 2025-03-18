package com.anypluspay.payment.facade.request;

import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

import java.util.List;

/**
 * @author wxj
 * 2024/1/15
 */
@Data
public class TradeInfo {

    /**
     * 交易号
     */
    private String tradeId;

    /**
     * 交易金额
     */
    private Money tradeAmount;

    /**
     * 付款方ID
     */
    private String payeeId;

    /**
     * 收款资金明细
     */
    private List<FundDetailInfo> payeeFundDetail;

}
