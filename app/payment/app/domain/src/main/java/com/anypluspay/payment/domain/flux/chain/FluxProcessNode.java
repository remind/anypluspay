package com.anypluspay.payment.domain.flux.chain;

import com.anypluspay.payment.domain.flux.FluxProcess;
import lombok.Data;

/**
 * 指令节点
 * @author wxj
 * 2025/2/7
 */
@Data
public class FluxProcessNode {

    /**
     * 指令
     */
    private FluxProcess fluxProcess;

    /**
     * 上一个
     */
    private FluxProcessNode prev;

    /**
     * 下一个
     */
    private FluxProcessNode next;

    public FluxProcessNode(FluxProcess fluxProcess) {
        this.fluxProcess = fluxProcess;
    }

    public void setPrev(FluxProcessNode prev) {
        this.prev = prev;
        fluxProcess.setPreProcessId(prev.getFluxProcess().getFluxProcessId());
    }
}
