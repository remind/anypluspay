package com.anypluspay.anypay.domain.pay;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 支付订单
 *
 * @author wxj
 * 2025/12/11
 */
@Data
public class PayOrder extends Entity {

    /**
     * 支付单号
     */
    private String payId;

    /**
     * 交易单号
     */
    private String tradeId;

}
