package com.anypluspay.channel.facade.fund;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.channel.application.route.RouteParam;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.commons.lang.utils.ExtUtil;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Arrays;

/**
 * 入款
 *
 * @author wxj
 * 2024/9/24
 */
@DubboService
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
        routeParam.setPayMethod(fundInOrder.getPayMethod());
        routeParam.setAmount(fundInOrder.getAmount());
        routeParam.setExtra(fundInOrder.getRouteExtra());
        if (StrUtil.isNotBlank(ExtUtil.getStringValue(ExtKey.WHITE_CHANNELS, fundInOrder.getRouteExtra()))) {
            routeParam.setWhiteChannels(Arrays.stream(ExtUtil.getStringValue(ExtKey.WHITE_CHANNELS, fundInOrder.getRouteExtra()).split(",")).toList());
        }
        return routeParam;
    }
}
