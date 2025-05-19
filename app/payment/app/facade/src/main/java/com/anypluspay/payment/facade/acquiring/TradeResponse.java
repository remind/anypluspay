package com.anypluspay.payment.facade.acquiring;

import com.anypluspay.commons.lang.BaseResult;
import com.anypluspay.commons.lang.std.ExtensionDeserializer;
import com.anypluspay.commons.lang.std.ExtensionSerializer;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @author wxj
 * 2025/5/18
 */
@Data
public class TradeResponse extends BaseResult {

    /**
     * 支付单号
     */
    private String paymentId;

    /**
     * 关联的支付业务单号
     */
    private String relationPaymentId;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 外部订单号
     */
    private String outTradeNo;

    /**
     * 合作方
     */
    private String partnerId;

    /**
     * 收款方
     */
    private String payeeId;

    /**
     * 收款方账户号
     */
    private String payeeAccountNo;

    /**
     * 付款方
     */
    private String payerId;

    /**
     * 标题
     */
    private String subject;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 状态
     */
    private String status;

    /**
     * 扩展信息
     */
    @JsonSerialize(using = ExtensionSerializer.class)
    @JsonDeserialize(using = ExtensionDeserializer.class)
    private Extension extension;
}
