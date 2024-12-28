package com.anypluspay.channel.facade;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.channel.application.institution.InstProcessService;
import com.anypluspay.channel.application.route.ChannelRouteService;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.service.InstOrderDomainService;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.facade.builder.InstOrderBuilder;
import com.anypluspay.channel.facade.fund.validator.FundValidatorService;
import com.anypluspay.channel.facade.result.ChannelResult;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.order.SubmitTimeType;
import com.anypluspay.channel.types.result.CsResultCode;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author wxj
 * 2024/7/18
 */
public abstract class AbstractChannelService {

    @Autowired
    protected InstProcessService instProcessService;

    @Autowired
    protected BizOrderRepository bizOrderRepository;

    @Autowired
    protected InstOrderRepository instOrderRepository;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Autowired
    protected InstOrderDomainService instOrderDomainService;

    @Autowired
    protected ChannelRouteService channelRouteService;

    @Autowired
    private InstOrderBuilder instOrderBuilder;

    @Autowired
    private FundValidatorService fundValidatorService;

    /**
     * 首次提交机构处理
     *
     * @param channelApiContext
     * @param bizOrder
     * @return
     */
    protected ChannelResult applyInstProcess(ChannelApiContext channelApiContext, BaseBizOrder bizOrder) {
        AssertUtil.notNull(channelApiContext, CsResultCode.NOT_EXIST_CHANNEL);
        OrderContext orderContext = createOrderContext(channelApiContext, bizOrder);
        if (orderContext.getInstCommandOrder() != null) {
            orderContext = instProcessService.submit(channelApiContext, orderContext);
        }
        ChannelResult result = buildChannelResult(orderContext.getBizOrder(), orderContext.getInstOrder(), orderContext.getInstCommandOrder());
        fillChannelResultCommon(result, orderContext.getBizOrder(), orderContext.getInstOrder(), orderContext.getInstCommandOrder());
        return result;
    }

    /**
     * 提交机构处理
     *
     * @param bizOrder
     * @param apiType
     * @return
     */
    protected ChannelResult applyInstProcess(BaseBizOrder bizOrder, ChannelApiType apiType) {
        InstOrder instOrder = instOrderRepository.load(bizOrder.getInstOrderId());
        ChannelApiContext channelApiContext = channelRouteService.routeByChannel(instOrder.getFundChannelCode(), apiType);
        OrderContext orderContext = instProcessService.createAndSubmit(channelApiContext, bizOrder, instOrder);
        ChannelResult result = buildChannelResult(orderContext.getBizOrder(), orderContext.getInstOrder(), orderContext.getInstCommandOrder());
        fillChannelResultCommon(result, orderContext.getBizOrder(), orderContext.getInstOrder(), orderContext.getInstCommandOrder());
        return result;
    }

    protected ChannelResult queryResultByOrderId(String orderId) {
        ChannelResult result;
        BaseBizOrder bizOrder = bizOrderRepository.load(orderId);
        AssertUtil.notNull(bizOrder, "订单不存在");
        InstOrder instOrder = instOrderRepository.load(bizOrder.getInstOrderId());
        InstCommandOrder instCommandOrder = null;
        if (instOrder.getStatus() != InstOrderStatus.INIT) {
            instCommandOrder = instOrderDomainService.loadMainProcessOrder(instOrder);
        }
        result = buildChannelResult(bizOrder, instOrder, instCommandOrder);
        fillChannelResultCommon(result, bizOrder, instOrder, instCommandOrder);
        return result;
    }

    protected void fillChannelResultCommon(ChannelResult result, BaseBizOrder bizOrder, InstOrder instOrder, InstCommandOrder instCommandOrder) {
        result.setRequestId(bizOrder.getRequestId());
        result.setOrderId(bizOrder.getOrderId());
        result.setFundChannelCode(instOrder.getFundChannelCode());
        if (instOrder.getStatus() == InstOrderStatus.INIT) {
            result.setUnityCode(CsResultCode.WAIT_SUBMIT_INST.getCode());
            result.setUnityMessage(CsResultCode.WAIT_SUBMIT_INST.getMessage());
        } else {
            result.setUnityCode(StrUtil.firstNonBlank(instCommandOrder.getUnityCode(), instCommandOrder.getApiCode()));
            result.setUnityMessage(StrUtil.firstNonBlank(instCommandOrder.getUnityMessage(), instCommandOrder.getApiMessage()));
        }
        result.setStatus(bizOrder.getStatus());
    }

    protected void fillChannelResultByBizException(ChannelResult result, BizException e) {
        result.setSuccess(false);
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
    }

    /**
     * 构造ChannelResult
     *
     * @param bizOrder
     * @param instOrder
     * @param instCommandOrder
     * @return
     */
    protected abstract ChannelResult buildChannelResult(BaseBizOrder bizOrder, InstOrder instOrder, InstCommandOrder instCommandOrder);

    /**
     * 创建订单
     * @param channelApiContext
     * @param bizOrder
     * @return
     */
    private OrderContext createOrderContext(ChannelApiContext channelApiContext, BaseBizOrder bizOrder) {
        OrderContext orderContext = new OrderContext();
        InstOrder instOrder = instOrderBuilder.buildInstOrder(channelApiContext, bizOrder);
        orderContext.setInstOrder(instOrder);
        orderContext.setBizOrder(bizOrder);
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            if (bizOrder instanceof RefundOrder refundOrder) {
                FundInOrder origOrder = (FundInOrder) bizOrderRepository.lock(refundOrder.getOrigOrderId());
                fundValidatorService.refundValidate(refundOrder, origOrder);
            }
            instOrderRepository.store(instOrder);
            bizOrder.setInstOrderId(instOrder.getInstOrderId());
            bizOrderRepository.store(bizOrder);
            if (instOrder.getSubmitTimeType() == SubmitTimeType.REAL) {
                orderContext.setInstCommandOrder(instOrderDomainService.createCommand(instOrder, channelApiContext.getChannelApiType()));
            }
        });
        return orderContext;
    }
}
