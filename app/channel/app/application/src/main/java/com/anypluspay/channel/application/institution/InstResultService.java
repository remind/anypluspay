package com.anypluspay.channel.application.institution;

import com.anypluspay.channel.application.event.BizOrderCompleteEvent;
import com.anypluspay.channel.application.institution.validator.InstResultValidator;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.bizorder.service.BizOrderService;
import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.service.InstOrderDomainService;
import com.anypluspay.channel.domain.repository.InstCommandOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.domain.result.UnityResult;
import com.anypluspay.channel.domain.result.service.ApiResultService;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.result.AssignResult;
import com.anypluspay.channel.types.result.ProcessResult;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.commons.exceptions.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


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
    private InstOrderRepository instOrderRepository;

    @Autowired
    private InstCommandOrderRepository instCommandOrderRepository;

    @Autowired
    private InstOrderDomainService instOrderDomainService;

    @Autowired
    private ApplicationContext applicationContext;

    public void process(ChannelApiContext channelApiContext, OrderContext orderContext, ProcessResult processResult) {
        fillProcessOrderResult(channelApiContext, orderContext, processResult);
        instOrderDomainService.turnToManual(channelApiContext, orderContext.getInstOrder(), orderContext.getInstCommandOrder());
        instCommandOrderRepository.reStore(orderContext.getInstCommandOrder());
        instOrderRepository.reStore(orderContext.getInstOrder());
        if (orderContext.getInstCommandOrder().getStatus() == InstOrderStatus.SUCCESS || orderContext.getInstCommandOrder().getStatus() == InstOrderStatus.FAILED) {
            bizOrderService.updateStatus(orderContext.getBizOrder(), orderContext.getInstOrder());
            applicationContext.publishEvent(new BizOrderCompleteEvent(orderContext));
        }
    }

    private void fillProcessOrderResult(ChannelApiContext channelApiContext, OrderContext orderContext, ProcessResult processResult) {
        if (processResult instanceof AssignResult assignGatewayResult) {
            orderContext.getInstCommandOrder().setStatus(assignGatewayResult.getOrderStatus());
            orderContext.getInstOrder().setStatus(assignGatewayResult.getOrderStatus());
        } else if (processResult instanceof GatewayResult generalGatewayResult) {
            if (generalGatewayResult.isSuccess()) {
                UnityResult unityResult;
                try {
                    InstResultValidator.validate(orderContext.getBizOrder(), orderContext.getInstOrder(), generalGatewayResult);
                    unityResult = apiResultService.doMatch(channelApiContext.getChannelCode(), channelApiContext.getChannelApi().getType(), generalGatewayResult.getApiCode(), generalGatewayResult.getApiSubCode(), generalGatewayResult.getApiMessage());
                    updateInstProcessOrderStatus(orderContext.getInstCommandOrder(), unityResult.getStatus(), unityResult.getResultCode(), unityResult.getResultMessage());
                    updateInstOrderStatus(orderContext.getInstOrder(), orderContext.getInstCommandOrder());
                } catch (BizException e) {
                    updateInstProcessOrderStatus(orderContext.getInstCommandOrder(), InstOrderStatus.UNKNOWN, e.getCode(), e.getMessage());
                    log.error("机构结果校验异常", e);
                }
            } else {
                orderContext.getInstCommandOrder().setStatus(InstOrderStatus.UNKNOWN);
            }
        }
        orderContext.getInstCommandOrder().setApiCode(processResult.getApiCode());
        orderContext.getInstCommandOrder().setApiSubCode(processResult.getApiSubCode());
        orderContext.getInstCommandOrder().setApiMessage(processResult.getApiMessage());
    }

    /**
     * 更新机构订单状态
     *
     * @param instOrder
     * @param instCommandOrder
     */
    private void updateInstOrderStatus(InstOrder instOrder, InstCommandOrder instCommandOrder) {
        switch (instCommandOrder.getStatus()) {
            case SUCCESS:
                instOrder.setStatus(InstOrderStatus.SUCCESS);
                break;
            case FAILED:
                instOrder.setStatus(InstOrderStatus.FAILED);
                break;
            case UNKNOWN:
                instOrder.setStatus(InstOrderStatus.PROCESSING);
                break;
            default:
                break;
        }
    }

    /**
     * 更新机构过程订单状态
     *
     * @param instCommandOrder
     * @param status
     * @param resultCode
     * @param resultMessage
     */
    private void updateInstProcessOrderStatus(InstCommandOrder instCommandOrder, InstOrderStatus status, String resultCode, String resultMessage) {
        instCommandOrder.setStatus(status);
        instCommandOrder.setUnityCode(resultCode);
        instCommandOrder.setUnityMessage(resultMessage);
    }


}
