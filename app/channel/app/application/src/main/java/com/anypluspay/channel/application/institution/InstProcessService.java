package com.anypluspay.channel.application.institution;

import com.anypluspay.channel.application.institution.gateway.GatewayRequestDispatcher;
import com.anypluspay.channel.application.institution.gateway.GatewayResultHandler;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.service.InstOrderDomainService;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.facade.request.NotifyRequest;
import com.anypluspay.channelgateway.api.verify.VerifyModel;
import com.anypluspay.channelgateway.request.RequestContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.commons.lang.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private InstOrderRepository instOrderRepository;

    @Autowired
    private InstOrderDomainService instOrderDomainService;

    @Autowired
    private GatewayResultHandler gatewayResultHandler;

    @Autowired
    private GatewaySubmitContentBuilder gatewaySubmitContentBuilder;

    /**
     * 创建并提交机构订单指令
     *
     * @param channelApiContext
     * @param bizOrder
     * @param instOrder
     * @return
     */
    public OrderContext createAndSubmit(ChannelApiContext channelApiContext, BaseBizOrder bizOrder, InstOrder instOrder) {
        InstCommandOrder instCommandOrder = instOrderDomainService.createCommand(instOrder, channelApiContext.getChannelApiType());
        return submit(channelApiContext, new OrderContext(bizOrder, instOrder, instCommandOrder));
    }

    /**
     * 提交机构订单指令
     *
     * @param channelApiContext
     * @param orderContext
     */
    public OrderContext submit(ChannelApiContext channelApiContext, OrderContext orderContext) {
        RequestContent requestContent = gatewaySubmitContentBuilder.buildRequestContent(channelApiContext, orderContext);
        GatewayResult gatewayResult = gatewayRequestDispatcher.doDispatch(channelApiContext, requestContent);
        return gatewayResultHandler.handle(gatewayResult, orderContext.getInstCommandOrder().getCommandId());
    }

    /**
     * 无订单处理
     *
     * @param channelApiContext
     * @param request
     * @return
     */
    public InstCommandOrder noneOrderProcess(ChannelApiContext channelApiContext, NotifyRequest request) {
        VerifyModel verifyModel = new VerifyModel(request.getRequestBody());
        GatewayResult gatewayResult = gatewayRequestDispatcher.doDispatch(channelApiContext, verifyModel);
        AssertUtil.notNull(gatewayResult.getInstRequestNo(), "处理异常");
        InstOrder instOrder = instOrderRepository.loadByInstRequestNo(gatewayResult.getInstRequestNo());
        AssertUtil.notNull(instOrder, "订单不存在");
        InstCommandOrder instCommandOrder = instOrderDomainService.createCommand(instOrder, channelApiContext.getChannelApiType());
        gatewayResultHandler.handle(gatewayResult, instCommandOrder.getCommandId());
        return instCommandOrder;
    }

}
