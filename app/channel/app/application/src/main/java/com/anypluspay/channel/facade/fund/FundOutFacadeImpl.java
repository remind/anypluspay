package com.anypluspay.channel.facade.fund;

import com.anypluspay.channel.application.route.ChannelRouteService;
import com.anypluspay.channel.application.route.RouteParam;
import com.anypluspay.channel.domain.bizorder.fund.FundOutOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.facade.FundOutFacade;
import com.anypluspay.channel.facade.request.FundOutRequest;
import com.anypluspay.channel.facade.result.FundResult;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2024/7/18
 */
@DubboService
public class FundOutFacadeImpl extends AbstractFundService implements FundOutFacade {

    @Autowired
    private ChannelRouteService channelRouteService;

    @Override
    public FundResult apply(FundOutRequest request) {
        FundOutOrder fundOutOrder = fundOrderBuilder.buildFundOut(request);
        RouteParam routeParam = buildRouteParam(fundOutOrder);
        ChannelApiContext channelApiContext = channelRouteService.routeOne(routeParam);
        return applyInstProcess(channelApiContext, fundOutOrder);
    }

    private RouteParam buildRouteParam(FundOutOrder fundOutOrder) {
        RouteParam routeParam = new RouteParam();
        routeParam.setRequestType(fundOutOrder.getRequestType());
        routeParam.setPayInst(fundOutOrder.getBankCode());
        routeParam.setAmount(fundOutOrder.getAmount());
        routeParam.setExtra(fundOutOrder.getRouteExtra());
        return routeParam;
    }

}
