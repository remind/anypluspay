package com.anypluspay.channel.application.route;

import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.types.channel.ChannelApiType;

import java.util.List;

/**
 * 资金渠道路由服务
 *
 * @author wxj
 * 2024/6/27
 */
public interface ChannelRouteService {

    /**
     * 渠道路由，返回一个可用的
     *
     * @param routeParam
     * @return
     */
    ChannelApiContext routeOne(RouteParam routeParam);

    /**
     * 渠道路由，返回所有可用的
     *
     * @param routeParam
     * @return
     */
    List<ChannelApiContext> route(RouteParam routeParam);

    /**
     * 根据渠道编码和API类型获取渠道API上下文
     * @param channelCode
     * @param apiType
     * @return
     */
    ChannelApiContext routeByChannel(String channelCode, ChannelApiType apiType);

}
