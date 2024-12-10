package com.anypluspay.channel.facade;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.channel.application.institution.InstProcessService;
import com.anypluspay.channel.application.route.ChannelRouteService;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.InstProcessOrder;
import com.anypluspay.channel.domain.institution.service.InstOrderService;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.facade.builder.InstOrderBuilder;
import com.anypluspay.channel.facade.fund.validator.FundValidatorService;
import com.anypluspay.channel.facade.result.ChannelResult;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.order.ProcessTimeType;
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
    protected InstOrderService instOrderService;

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
        InstOrder instOrder = instOrderBuilder.buildInstOrder(channelApiContext, bizOrder);

        transactionTemplate.executeWithoutResult(transactionStatus -> {
            if (bizOrder instanceof RefundOrder refundOrder) {
                FundInOrder origOrder = (FundInOrder) bizOrderRepository.lock(refundOrder.getOrigOrderId());
                fundValidatorService.refundValidate(refundOrder, origOrder);
            }
            bizOrder.setInstOrderId(instOrder.getInstOrderId());
            bizOrderRepository.store(bizOrder);
            instOrderRepository.store(instOrder);
        });
        InstProcessOrder instProcessOrder = null;
        if (instOrder.getProcessTimeType() == ProcessTimeType.REAL) {
            instProcessOrder = instProcessService.process(channelApiContext, bizOrder, instOrder);
        }
        ChannelResult result = buildChannelResult(bizOrder, instOrder, instProcessOrder);
        fillChannelResultCommon(result, bizOrder, instOrder, instProcessOrder);
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
        InstProcessOrder instProcessOrder = instProcessService.process(channelApiContext, bizOrder, instOrder);
        ChannelResult result = buildChannelResult(bizOrder, instOrder, instProcessOrder);
        fillChannelResultCommon(result, bizOrder, instOrder, instProcessOrder);
        return result;
    }

    protected ChannelResult queryResultByOrderId(String orderId) {
        ChannelResult result;
        BaseBizOrder bizOrder = bizOrderRepository.load(orderId);
        AssertUtil.notNull(bizOrder, "订单不存在");
        InstOrder instOrder = instOrderRepository.load(bizOrder.getInstOrderId());
        InstProcessOrder instProcessOrder = null;
        if (instOrder.getStatus() != InstOrderStatus.INIT) {
            instProcessOrder = instOrderService.loadMainProcessOrder(instOrder);
        }
        result = buildChannelResult(bizOrder, instOrder, instProcessOrder);
        fillChannelResultCommon(result, bizOrder, instOrder, instProcessOrder);
        return result;
    }

    protected void fillChannelResultCommon(ChannelResult result, BaseBizOrder bizOrder, InstOrder instOrder, InstProcessOrder instProcessOrder) {
        result.setRequestId(bizOrder.getRequestId());
        result.setOrderId(bizOrder.getOrderId());
        result.setFundChannelCode(instOrder.getFundChannelCode());
        if (instOrder.getStatus() == InstOrderStatus.INIT) {
            result.setUnityCode(CsResultCode.WAIT_SUBMIT_INST.getCode());
            result.setUnityMessage(CsResultCode.WAIT_SUBMIT_INST.getMessage());
        } else {
            result.setUnityCode(StrUtil.firstNonBlank(instProcessOrder.getUnityCode(), instProcessOrder.getApiCode()));
            result.setUnityMessage(StrUtil.firstNonBlank(instProcessOrder.getUnityMessage(), instProcessOrder.getApiMessage()));
        }
        result.setStatus(bizOrder.getStatus());
    }

    protected void fillChannelResultByBizException(ChannelResult result, BizException e) {
        result.setSuccess(false);
        result.setCode(e.getResultCode().getCode());
        result.setMessage(e.getResultCode().getMessage());
    }

    /**
     * 构造ChannelResult
     *
     * @param bizOrder
     * @param instOrder
     * @param instProcessOrder
     * @return
     */
    protected abstract ChannelResult buildChannelResult(BaseBizOrder bizOrder, InstOrder instOrder, InstProcessOrder instProcessOrder);
}
