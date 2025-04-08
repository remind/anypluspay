package com.anypluspay.payment.domain.flux;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.types.asset.AssetInfo;
import com.anypluspay.payment.types.funds.FundAction;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

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

    private String relationId;

    private Money amount;

    private String fundDetailId;

    private InstructStatus status;

    /**
     * 资金动作
     */
    private FundAction fundAction;

    /**
     * 资产信息
     */
    private AssetInfo assetInfo;

    private Map<String, String> extMap = new HashMap<>();

    public String getExtValue(String key) {
        return extMap == null ? null : extMap.get(key);
    }

    public void putExtValue(String key, String value) {
        extMap.put(key, value);
    }

}
