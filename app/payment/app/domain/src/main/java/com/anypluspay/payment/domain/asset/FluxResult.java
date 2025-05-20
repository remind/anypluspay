package com.anypluspay.payment.domain.asset;

import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.payment.domain.flux.FluxProcess;
import com.anypluspay.payment.types.PayStatus;
import lombok.Data;

import java.util.List;

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
    private FluxProcess executeInstruction;

    /**
     * 新增交换指令
     */
    private List<FluxProcess> newFluxProcesses;

    /**
     * 返回结果码
     */
    private String resultCode;

    /**
     * 返回信息
     */
    private String resultMessage;

    /**
     * 交换响应
     */
    private Extension fluxResponse = new Extension();

}
