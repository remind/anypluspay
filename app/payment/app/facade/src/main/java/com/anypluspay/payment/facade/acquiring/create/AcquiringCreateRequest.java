package com.anypluspay.payment.facade.acquiring.create;

import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

/**
 * 交易创建请求
 * @author wxj
 * 2025/5/17
 */
@Data
public class AcquiringCreateRequest {
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
     * 过期时间，分钟
     */
    private Integer expireMinutes;

    /**
     * 扩展信息
     */
    private String extension;
}
