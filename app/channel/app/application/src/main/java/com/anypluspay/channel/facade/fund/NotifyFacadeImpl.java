package com.anypluspay.channel.facade.fund;

import com.anypluspay.channel.application.context.ChannelContext;
import com.anypluspay.channel.application.institution.InstProcessService;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.facade.NotifyFacade;
import com.anypluspay.channel.facade.request.NotifyRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.lang.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxj
 * 2024/8/19
 */
@RestController
public class NotifyFacadeImpl extends AbstractFundService implements NotifyFacade {

    @Autowired
    private InstProcessService instProcessService;

    @Autowired
    private InstOrderRepository instOrderRepository;

    @Autowired
    private BizOrderRepository bizOrderRepository;

    @Override
    public FundResult notify(NotifyRequest request) {
        ChannelApiType apiType = ChannelApiType.getByCode(request.getChannelApiType());
        AssertUtil.notNull(apiType, "apiType不能为null");
        if (StringUtils.isNotBlank(request.getInstRequestNo())) {
            try {
                ChannelContext.set(request);
                return notifyByInstRequestNo(apiType, request.getInstRequestNo());
            } finally {
                ChannelContext.clear();
            }
        } else {
            ChannelApiContext channelApiContext = channelRouteService.routeByChannel(request.getChannelCode(), apiType);
            AssertUtil.notNull(channelApiContext, "无可用渠道");
            InstCommandOrder instCommandOrder = instProcessService.noneOrderProcess(channelApiContext, request);
            InstOrder instOrder = instOrderRepository.load(instCommandOrder.getInstOrderId());
            BaseBizOrder bizOrder = bizOrderRepository.load(instOrder.getBizOrderId());
            FundResult result = (FundResult) buildChannelResult(bizOrder, instOrder, instCommandOrder);
            fillChannelResultCommon(result, bizOrder, instOrder, instCommandOrder);
            return result;
        }
    }

    private FundResult notifyByInstRequestNo(ChannelApiType channelApiType, String instRequestNo) {
        InstOrder instOrder = instOrderRepository.loadByInstRequestNo(instRequestNo);
        AssertUtil.notNull(instOrder, "机构订单不存在");
        BaseBizOrder bizOrder = bizOrderRepository.load(instOrder.getBizOrderId());
        AssertUtil.notNull(bizOrder, "订单不存在");
        ChannelApiContext channelApiContext = channelRouteService.routeByChannel(instOrder.getFundChannelCode(), channelApiType);
        AssertUtil.notNull(channelApiContext, "无可用渠道");
        if (bizOrder.getStatus() == BizOrderStatus.PROCESSING) {
            return applyInstProcess(bizOrder, channelApiType);
        } else {
            return (FundResult) queryResultByOrderId(bizOrder.getOrderId());
        }
    }

}
