package com.anypluspay.anypay.trade;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 交易单
 *
 * @author wxj
 * 2025/5/17
 */
@Data
public abstract class BaseTradeOrder extends Entity {

    /**
     * 交易单号
     */
    private String tradeId;

    /**
     * 合作方
     */
    private String partnerId;

    /**
     * 支付单号ID
     */
    private String orderId;
}
