package com.anypluspay.channel.application.institution;

import com.anypluspay.channel.application.institution.validator.InstResultValidator;
import com.anypluspay.channel.types.result.AssignResult;
import com.anypluspay.channel.types.result.ProcessResult;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channel.facade.builder.InstProcessOrderBuilder;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.service.BizOrderService;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.InstProcessOrder;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.domain.repository.InstProcessOrderRepository;
import com.anypluspay.channel.domain.result.UnityResult;
import com.anypluspay.channel.domain.result.service.ApiResultService;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.order.InstProcessOrderStatus;
import com.anypluspay.commons.exceptions.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * 机构结果处理
 *
 * @author wxj
 * 2024/7/12
 */
@Service
@Slf4j
public class InstResultService {

    @Autowired
    private ApiResultService apiResultService;

    @Autowired
    private BizOrderService bizOrderService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private InstOrderRepository instOrderRepository;

    @Autowired
    private InstProcessOrderRepository instProcessOrderRepository;

    @Autowired
    private InstProcessOrderBuilder instProcessOrderBuilder;

    public void process(ChannelApiContext channelApiContext, BaseBizOrder bizOrder, InstOrder instOrder, InstProcessOrder instProcessOrder, ProcessResult processResult) {
        fillProcessOrderResult(channelApiContext, bizOrder, instOrder, instProcessOrder, processResult);
        InstProcessOrder manualProcessOrder = toManualProcess(channelApiContext, instProcessOrder);

        transactionTemplate.executeWithoutResult(status -> {
            instProcessOrderRepository.reStore(instProcessOrder);

            if (manualProcessOrder != null) {
                instProcessOrderRepository.store(manualProcessOrder);
            }

            instOrderRepository.reStore(instOrder);

            if (instProcessOrder.getStatus() == InstProcessOrderStatus.SUCCESS || instProcessOrder.getStatus() == InstProcessOrderStatus.FAILED) {
                bizOrderService.updateStatus(bizOrder, instOrder);
            }
        });
    }

    private void fillProcessOrderResult(ChannelApiContext channelApiContext, BaseBizOrder bizOrder, InstOrder instOrder, InstProcessOrder instProcessOrder, ProcessResult processResult) {
        if (processResult instanceof AssignResult assignGatewayResult) {
            instProcessOrder.setStatus(assignGatewayResult.getProcessOrderStatus());
            instOrder.setStatus(assignGatewayResult.getOrderStatus());
        } else if (processResult instanceof GatewayResult generalGatewayResult) {
            if (generalGatewayResult.isSuccess()) {
                UnityResult unityResult;
                try {
                    InstResultValidator.validate(bizOrder, instOrder, generalGatewayResult);
                    unityResult = apiResultService.doMatch(channelApiContext.getChannelCode(), channelApiContext.getChannelApi().getType(), generalGatewayResult.getApiCode(), generalGatewayResult.getApiSubCode(), generalGatewayResult.getApiMessage());
                    updateInstProcessOrderStatus(instProcessOrder, unityResult.getStatus(), unityResult.getResultCode(), unityResult.getResultMessage());
                    updateInstOrderStatus(instOrder, instProcessOrder);
                } catch (BizException e) {
                    updateInstProcessOrderStatus(instProcessOrder, InstProcessOrderStatus.UNKNOWN, e.getResultCode().getCode(), e.getMessage());
                    log.error("机构结果校验异常", e);
                }
            } else {
                instProcessOrder.setStatus(InstProcessOrderStatus.UNKNOWN);
            }
        }
        instProcessOrder.setApiCode(processResult.getApiCode());
        instProcessOrder.setApiSubCode(processResult.getApiSubCode());
        instProcessOrder.setApiMessage(processResult.getApiMessage());
    }

    /**
     * 更新机构订单状态
     *
     * @param instOrder
     * @param instProcessOrder
     */
    private void updateInstOrderStatus(InstOrder instOrder, InstProcessOrder instProcessOrder) {
        switch (instProcessOrder.getStatus()) {
            case SUCCESS:
                instOrder.setStatus(InstOrderStatus.SUCCESS);
                break;
            case FAILED:
                instOrder.setStatus(InstOrderStatus.FAILED);
                break;
            default:
                break;
        }
    }

    /**
     * 更新机构过程订单状态
     *
     * @param instProcessOrder
     * @param status
     * @param resultCode
     * @param resultMessage
     */
    private void updateInstProcessOrderStatus(InstProcessOrder instProcessOrder, InstProcessOrderStatus status, String resultCode, String resultMessage) {
        instProcessOrder.setStatus(status);
        instProcessOrder.setUnityCode(resultCode);
        instProcessOrder.setUnityMessage(resultMessage);
    }

    /**
     * 失败转人工处理
     * 如果返回人工处理单就转人工，如果返回null就不用
     *
     * @param channelApiContext
     * @param instProcessOrder
     * @return
     */
    private InstProcessOrder toManualProcess(ChannelApiContext channelApiContext, InstProcessOrder instProcessOrder) {
        InstProcessOrder manualProcessOrder = null;
        if (instProcessOrder.getStatus() == InstProcessOrderStatus.FAILED
                && !ChannelApiType.isManual(instProcessOrder.getApiType())) {
            if (channelApiContext.getChannelApiType() == ChannelApiType.SINGLE_REFUND) {
                manualProcessOrder = instProcessOrderBuilder.buildManual(instProcessOrder, ChannelApiType.MANUAL_REFUND);
            }
        }
        return manualProcessOrder;
    }

}
