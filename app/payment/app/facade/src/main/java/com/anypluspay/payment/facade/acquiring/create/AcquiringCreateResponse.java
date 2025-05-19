package com.anypluspay.payment.facade.acquiring.create;

import com.anypluspay.commons.lang.BaseResult;
import lombok.Data;

/**
 * 交易创建响应
 * @author wxj
 * 2025/5/17
 */
@Data
public class AcquiringCreateResponse extends BaseResult {

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

}
