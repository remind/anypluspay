package com.anypluspay.channel.application.institution;

import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channel.application.institution.gateway.GatewayRequestDispatcher;
import com.anypluspay.channel.facade.builder.InstProcessOrderBuilder;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.InstProcessOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.domain.repository.InstProcessOrderRepository;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.order.ProcessTimeType;
import com.anypluspay.commons.lang.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;

/**
 * 机构侧处理服务
 *
 * @author wxj
 * 2024/6/29
 */
@Service
public class InstProcessService {

    @Autowired
    private GatewayRequestDispatcher gatewayRequestDispatcher;

    @Autowired
    private InstResultService instResultService;

    @Autowired
    private InstOrderRepository instOrderRepository;

    @Autowired
    private BizOrderRepository bizOrderRepository;

    @Autowired
    private InstProcessOrderRepository instProcessOrderRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private InstProcessOrderBuilder instProcessOrderBuilder;

    /**
     * 正向处理
     * @param channelApiContext
     * @param bizOrder
     * @param instOrder
     * @return
     */
    public InstProcessOrder process(ChannelApiContext channelApiContext, BaseBizOrder bizOrder, InstOrder instOrder) {
        InstProcessOrder instProcessOrder = instProcessOrderBuilder.build(channelApiContext, instOrder);
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            instProcessOrderRepository.store(instProcessOrder);
            if (instOrder.getProcessTimeType() == ProcessTimeType.DELAYED && instOrder.getStatus() == InstOrderStatus.INIT) {
                instOrder.setProcessTime(LocalDateTime.now());
                instOrder.setStatus(InstOrderStatus.PROCESSING);
                instOrderRepository.reStore(instOrder);
            }
        });
        GatewayResult gatewayResult = gatewayRequestDispatcher.doDispatch(channelApiContext, new OrderContext(bizOrder, instOrder, instProcessOrder));
        instResultService.process(channelApiContext, bizOrder, instOrder, instProcessOrder, gatewayResult);
        return instProcessOrder;
    }

    /**
     * 无订单处理
     * @param channelApiContext
     * @param requestBody
     * @return
     */
    public InstProcessOrder noneOrderProcess(ChannelApiContext channelApiContext, String requestBody) {
        GatewayResult gatewayResult = gatewayRequestDispatcher.doNoneOrderDispatch(channelApiContext, requestBody);
        AssertUtil.notNull(gatewayResult.getInstRequestNo(), "异常处理");
        InstOrder instOrder = instOrderRepository.loadByInstRequestNo(gatewayResult.getInstRequestNo());
        AssertUtil.notNull(instOrder, "订单不存在");
        InstProcessOrder instProcessOrder = instProcessOrderBuilder.build(channelApiContext, instOrder);
        BaseBizOrder bizOrder = bizOrderRepository.load(instOrder.getBizOrderId());
        gatewayRequestDispatcher.processResult(channelApiContext, new OrderContext(bizOrder, instOrder, instProcessOrder), gatewayResult);
        instProcessOrderRepository.store(instProcessOrder);
        instResultService.process(channelApiContext, bizOrder,instOrder, instProcessOrder, gatewayResult);
        return instProcessOrder;
    }

}
