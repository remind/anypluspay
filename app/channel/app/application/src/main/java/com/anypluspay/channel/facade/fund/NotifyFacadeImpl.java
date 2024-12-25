package com.anypluspay.channel.facade.fund;

import cn.hutool.core.lang.Assert;
import com.anypluspay.channel.application.institution.InstProcessService;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.facade.NotifyFacade;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.channel.ChannelApiType;
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
    public FundResult notify(String channelCode, ChannelApiType apiType, String request) {
        ChannelApiContext channelApiContext = channelRouteService.routeByChannel(channelCode, apiType);
        Assert.notNull(channelApiContext, "无可用渠道");
        InstCommandOrder instCommandOrder = instProcessService.noneOrderProcess(channelApiContext, request);
        InstOrder instOrder = instOrderRepository.load(instCommandOrder.getInstOrderId());
        BaseBizOrder bizOrder = bizOrderRepository.load(instOrder.getBizOrderId());
        FundResult result = (FundResult) buildChannelResult(bizOrder, instOrder, instCommandOrder);
        fillChannelResultCommon(result, bizOrder, instOrder, instCommandOrder);
        return result;
    }

}
