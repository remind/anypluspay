package com.anypluspay.payment.domain.flux.service;

import com.anypluspay.payment.domain.flux.*;
import com.anypluspay.payment.domain.flux.chain.FluxChain;
import com.anypluspay.payment.domain.repository.FluxProcessRepository;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.IdType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 交换订单领域服务
 *
 * @author wxj
 * 2024/1/26
 */
@Service
public class FluxOrderService {

    @Autowired
    private FluxProcessRepository fluxProcessRepository;

    @Autowired
    protected IdGeneratorService idGeneratorService;

    /**
     * 删除指定指令后面的指令
     *
     * @param fluxOrder
     * @param instructId
     * @return
     */
    public void deleteAfterFluxInstruct(FluxOrder fluxOrder, String instructId) {
        List<String> deleteIds = fluxOrder.getFluxChain().deleteAfterNode(instructId);
        fluxProcessRepository.remove(deleteIds);
    }


    /**
     * 创建一个指令的逆向指令
     *
     * @param fluxOrder
     * @param fluxProcess
     * @return
     */
    public FluxProcess createRevokeInstruct(FluxOrder fluxOrder, FluxProcess fluxProcess) {
        FluxProcess reverseInstruct = new FluxProcess();
        FluxChain fluxChain = fluxOrder.getFluxChain();
        reverseInstruct.setFluxProcessId(idGeneratorService.genIdByRelateId(fluxProcess.getFluxOrderId(), IdType.FLUX_INSTRUCT_ID));
        reverseInstruct.setFluxOrderId(fluxProcess.getFluxOrderId());
        reverseInstruct.setOrderId(fluxProcess.getOrderId());
        reverseInstruct.setTradeId(fluxProcess.getTradeId());
        reverseInstruct.setType(fluxProcess.getType());
        reverseInstruct.setDirection(FluxProcessDirection.REVOKE);
        reverseInstruct.setAmount(fluxProcess.getAmount());
        reverseInstruct.setStatus(FluxProcessStatus.INIT);
        reverseInstruct.setFundDetailId(fluxProcess.getFundDetailId());
        reverseInstruct.setRelationId(fluxProcess.getFluxProcessId());
        reverseInstruct.setFundAction(fluxProcess.getFundAction().reverse());
        reverseInstruct.setAssetInfo(fluxProcess.getAssetInfo());
        fluxChain.append(reverseInstruct);
        fluxProcessRepository.store(reverseInstruct);
        return reverseInstruct;
    }

    /**
     * 在指定指令之后插入指令
     *
     * @param fluxOrder
     * @param fluxProcess
     * @param newFluxProcesses
     * @return
     */
    public void insertInstruct(FluxOrder fluxOrder, FluxProcess fluxProcess, List<FluxProcess> newFluxProcesses) {
        if (!CollectionUtils.isEmpty(newFluxProcesses)) {
            FluxProcess changedInstruction = fluxOrder.getFluxChain().insertAfterNode(fluxProcess, newFluxProcesses);
            fluxProcessRepository.reStore(changedInstruction);
            newFluxProcesses.forEach(fluxProcessRepository::store);
        }
    }
}
