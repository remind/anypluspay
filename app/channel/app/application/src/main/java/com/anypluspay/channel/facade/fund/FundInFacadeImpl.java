package com.anypluspay.channel.facade.fund;

import com.anypluspay.channel.application.route.RouteParam;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ChannelExtKey;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 入款
 *
 * @author wxj
 * 2024/9/24
 */
@RestController
public class FundInFacadeImpl extends AbstractFundService implements FundInFacade {

    @Override
    public FundResult apply(FundInRequest request) {
        FundInOrder fundInOrder = fundOrderBuilder.buildFundIn(request);
        RouteParam routeParam = buildRouteParam(fundInOrder);
        ChannelApiContext channelApiContext = channelRouteService.routeOne(routeParam);
        return applyInstProcess(channelApiContext, fundInOrder);
    }

    private RouteParam buildRouteParam(FundInOrder fundInOrder) {
        RouteParam routeParam = new RouteParam();
        routeParam.setRequestType(fundInOrder.getRequestType());
        routeParam.setPayInst(fundInOrder.getPayInst());
        routeParam.setPayMethod(fundInOrder.getPayModel());
        routeParam.setAmount(fundInOrder.getAmount());
        routeParam.setExtension(fundInOrder.getExtension());
        if (fundInOrder.getExtension().containsKey(ChannelExtKey.WHITE_CHANNELS.getCode())) {
            routeParam.setWhiteChannels(Arrays.stream(fundInOrder.getExtension().get(ChannelExtKey.WHITE_CHANNELS.getCode()).split(",")).toList());
        }
        return routeParam;
    }
}
