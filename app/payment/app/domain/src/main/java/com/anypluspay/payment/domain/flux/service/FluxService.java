package com.anypluspay.payment.domain.flux.service;

import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.*;
import com.anypluspay.payment.domain.repository.FluxProcessRepository;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.PayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 交换服务
 *
 * @author wxj
 * 2025/2/22
 */
@Service
public class FluxService {

    @Autowired
    private FluxOrderRepository fluxOrderRepository;

    @Autowired
    private FluxProcessRepository instructionRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private FluxOrderService fluxOrderService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public void process(FluxOrder fluxOrder, FluxProcess fluxProcess, FluxResult fluxResult) {
        fluxProcess.setStatus(convertToInstructStatus(fluxResult.getStatus()));
        transactionTemplate.executeWithoutResult(status -> {
            FluxOrder lockFluxOrder = fluxOrderRepository.lock(fluxOrder.getFluxOrderId());

            fluxProcess.setResultCode(fluxResult.getResultCode());
            fluxProcess.setResultMsg(fluxResult.getResultMessage());

            if (fluxProcess.getStatus() == FluxProcessStatus.SUCCESS) {
                addNewRelateInstruction(fluxOrder, fluxProcess, fluxResult.getNewFluxProcesses());

                boolean isEnd = fluxOrder.getFluxChain().getTail().getFluxProcess().getFluxProcessId().equals(fluxProcess.getFluxProcessId());
                if (isEnd) {
                    successProcess(fluxOrder, fluxProcess);
                }
            } else if (fluxProcess.getStatus() == FluxProcessStatus.FAIL) {
                failProcess(fluxOrder, fluxProcess);
            }

            instructionRepository.reStore(fluxProcess);
            if (lockFluxOrder.getStatus() != fluxOrder.getStatus() && lockFluxOrder.getStatus() == FluxOrderStatus.PROCESS) {
                fluxOrderRepository.reStore(fluxOrder);
            }
        });
    }

    /**
     * 成功处理
     *
     * @param fluxOrder
     * @param lastInstruction
     */
    private void successProcess(FluxOrder fluxOrder, FluxProcess lastInstruction) {
        long count = fluxOrder.getFluxChain().toList().stream().filter(i -> i.getStatus() != FluxProcessStatus.SUCCESS).count();
        if (count == 0) {
            fluxOrder.setStatus(FluxOrderStatus.SUCCESS);
            fluxOrder.setResultCode(lastInstruction.getResultCode());
            fluxOrder.setResultMsg(lastInstruction.getResultMsg());
        }
    }

    /**
     * 失败处理
     *
     * @param fluxOrder
     * @param lastInstruction
     */
    private void failProcess(FluxOrder fluxOrder, FluxProcess lastInstruction) {
        if (lastInstruction.getDirection() == FluxProcessDirection.APPLY) {
            // 删除所有后置指令
            fluxOrderService.deleteAfterFluxInstruct(fluxOrder, lastInstruction.getFluxProcessId());

            // 支付撤消可生成退款单，退款撤消暂不处理
            if (fluxOrder.getPayType() == PayOrderType.PAY) {
                addRevokeInstruct(fluxOrder);
            }
        }
        fluxOrder.setStatus(FluxOrderStatus.FAIL);
        fluxOrder.setResultCode(lastInstruction.getResultCode());
        fluxOrder.setResultMsg(lastInstruction.getResultMsg());
    }

    /**
     * 新增撤消指令
     *
     * @param fluxOrder
     */
    private void addRevokeInstruct(FluxOrder fluxOrder) {
        List<FluxProcess> forwardInstructs = fluxOrder.getFluxChain().toList().stream()
                .filter(assetFluxInstruct -> assetFluxInstruct.getStatus() == FluxProcessStatus.SUCCESS
                        && assetFluxInstruct.getType() == FluxProcessType.NORMAL)
                .toList();
        if (!CollectionUtils.isEmpty(forwardInstructs)) {
            forwardInstructs.forEach(assetFluxInstruct -> {
                fluxOrderService.createRevokeInstruct(fluxOrder, assetFluxInstruct);
            });
        }
    }

    /**
     * 新增关联指令
     *
     * @param fluxOrder
     * @param fluxProcess
     * @param newFluxProcesses
     */
    private void addNewRelateInstruction(FluxOrder fluxOrder, FluxProcess fluxProcess, List<FluxProcess> newFluxProcesses) {
        if (!CollectionUtils.isEmpty(newFluxProcesses)) {
            for (FluxProcess newFluxProcess : newFluxProcesses) {
                newFluxProcess.setTradeId(fluxProcess.getTradeId());
                newFluxProcess.setOrderId(fluxProcess.getOrderId());
                newFluxProcess.setFluxOrderId(fluxOrder.getFluxOrderId());
                newFluxProcess.setFluxProcessId(idGeneratorService.genIdByRelateId(fluxProcess.getFluxProcessId(), IdType.FLUX_INSTRUCT_ID));
                newFluxProcess.setStatus(FluxProcessStatus.INIT);
            }
            fluxOrderService.insertInstruct(fluxOrder, fluxProcess, newFluxProcesses);
        }
    }

    /**
     * 状态转换
     *
     * @param payStatus
     * @return
     */
    private FluxProcessStatus convertToInstructStatus(PayStatus payStatus) {
        return switch (payStatus) {
            case SUCCESS -> FluxProcessStatus.SUCCESS;
            case FAIL -> FluxProcessStatus.FAIL;
            case PROCESS -> FluxProcessStatus.PROCESS;
        };
    }
}
