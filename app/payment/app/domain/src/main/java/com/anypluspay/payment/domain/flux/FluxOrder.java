package com.anypluspay.payment.domain.flux;

import com.anypluspay.commons.lang.Entity;
import com.anypluspay.payment.domain.flux.chain.InstructChain;
import lombok.Data;

/**
 * 资产交换单
 *
 * @author wxj
 * 2024/1/20
 */
@Data
public class FluxOrder extends Entity {

    /**
     * 支付总单ID
     */
    private String paymentId;

    /**
     * 支付订单ID
     */
    private String payOrderId;

    /**
     * 交换ID
     */
    private String fluxOrderId;

    /**
     * 交换状态
     */
    private FluxOrderStatus status;

    /**
     * 关联ID
     */
    private String relationId;

    /**
     * 指令
     */
    private InstructChain instructChain;

}
