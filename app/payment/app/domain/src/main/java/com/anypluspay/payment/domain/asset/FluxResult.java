package com.anypluspay.payment.domain.asset;

import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.types.PayStatus;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交换结果
 *
 * @author wxj
 * 2024/1/26
 */
@Data
public class FluxResult {

    /**
     * 支付状态
     */
    private PayStatus status;

    /**
     * 执行指令
     */
    private FluxInstruction executeInstruction;

    /**
     * 新增交换指令
     */
    private List<FluxInstruction> newFluxInstructions;

    /**
     * 返回结果码
     */
    private String resultCode;

    /**
     * 返回信息
     */
    private String resultMessage;

    /**
     * 支付参数
     */
    private Map<String, String> payParam = new HashMap<>();

    /**
     * 扩展信息
     */
    private Map<String, String> extMap = new HashMap<>();
}
