package com.anypluspay.channel.facade.fund;

import com.anypluspay.channel.application.institution.InstProcessService;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.InstProcessOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.facade.NotifyFacade;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.channel.ChannelApiType;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2024/8/19
 */
@DubboService
public class NotifyServiceImpl extends AbstractFundService implements NotifyFacade {

    @Autowired
    private InstProcessService instProcessService;

    @Autowired
    private InstOrderRepository instOrderRepository;

    @Autowired
    private BizOrderRepository bizOrderRepository;

    @Override
    public FundResult notify(String fundChannelCode, String request) {
        ChannelApiContext channelApiContext = channelRouteService.routeByChannel(fundChannelCode,ChannelApiType.VERIFY_SIGN);
        InstProcessOrder instProcessOrder = instProcessService.noneOrderProcess(channelApiContext, request);
        InstOrder instOrder = instOrderRepository.load(instProcessOrder.getInstOrderId());
        BaseBizOrder bizOrder = bizOrderRepository.load(instOrder.getBizOrderId());
        FundResult result = (FundResult) buildChannelResult(bizOrder, instOrder, instProcessOrder);
        fillChannelResultCommon(result, bizOrder, instOrder, instProcessOrder);
        return result;
    }

}
