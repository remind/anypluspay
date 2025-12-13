package com.anypluspay.anypay.payment.instruct;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 支付指令
 * @author wxj
 * 2025/12/11
 */
@Data
public class PayInstruct extends Entity {

    /**
     * 指令ID
     */
    private String instructId;

    /**
     * 支付方式ID
     */
    private String payMethodId;
}
