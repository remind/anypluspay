package com.anypluspay.payment.domain.flux;

import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.types.asset.AssetInfo;
import com.anypluspay.payment.types.funds.FundAction;
import lombok.Data;

/**
 * 交换指令
 * @author wxj
 * 2024/1/25
 */
@Data
public class FluxInstruction {

    /**
     * 指令ID
     */
    private String instructionId;

    /**
     * 上一条指令ID
     */
    private String preInstructionId;

    /**
     * 支付总单ID
     */
    private String paymentId;

    /**
     * 支付订单ID
     */
    private String payOrderId;

    /**
     * 资产交换订单ID
     */
    private String fluxOrderId;

    /**
     * 指令类型
     */
    private InstructionType type;

    /**
     * 关联指令，如退款指令关联的支付指令
     */
    private String relationId;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 资产详情ID
     */
    private String fundDetailId;

    /**
     * 指令状态
     */
    private InstructStatus status;

    /**
     * 资金动作
     */
    private FundAction fundAction;

    /**
     * 资产信息
     */
    private AssetInfo assetInfo;

    /**
     * 执行结果码
     */
    private String resultCode;

    /**
     * 执行结果信息
     */
    private String resultMsg;

    /**
     * 扩展信息
     */
    private Extension extension;

}
