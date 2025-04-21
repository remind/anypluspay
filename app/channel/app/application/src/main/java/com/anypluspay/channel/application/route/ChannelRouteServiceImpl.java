package com.anypluspay.channel.application.route;

import cn.hutool.core.collection.CollectionUtil;
import com.anypluspay.channel.application.route.scorer.ChannelScorer;
import com.anypluspay.channel.application.route.scorer.api.ChannelApiScorer;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.channel.ChannelFullInfo;
import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channel.domain.repository.channel.ChannelFullInfoRepository;
import com.anypluspay.channel.types.channel.ChannelApiType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author wxj
 * 2024/6/28
 */
@Service
public class ChannelRouteServiceImpl implements ChannelRouteService {

    @Autowired
    private List<ChannelScorer> channelScorers;

    @Autowired
    private List<ChannelApiScorer> channelApiScorers;

    @Autowired
    private ChannelFullInfoRepository channelFullInfoRepository;

    @Override
    public ChannelApiContext routeOne(RouteParam routeParam) {
        List<ChannelApiContext> route = route(routeParam);
        return CollectionUtil.isEmpty(route) ? null : route.get(0);
    }

    @Override
    public List<ChannelApiContext> route(RouteParam routeParam) {
        List<ChannelFullInfo> channelFullInfos = channelFullInfoRepository.getAllAvailableChannels(routeParam.getRequestType(), routeParam.getPayModel());
        List<ChannelHolder> channelHolders = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(channelFullInfos)) {
            if (CollectionUtil.isNotEmpty(routeParam.getWhiteChannels())) {
                channelFullInfos.stream().filter(channelFullInfo -> routeParam.getWhiteChannels().contains(channelFullInfo.getChannelCode()))
                        .forEach(channelFullInfo -> fillChannelHolder(routeParam, channelFullInfo, channelHolders, ChannelScorer.STANDARD_SCORE));
            } else {
                for (ChannelFullInfo channelFullInfo : channelFullInfos) {
                    int score = scoreChannel(channelFullInfo, routeParam);
                    if (score > ChannelScorer.ZERO_SCORE) {
                        fillChannelHolder(routeParam, channelFullInfo, channelHolders, score);
                    }
                }
            }
        }
        if (CollectionUtil.isNotEmpty(channelHolders)) {
            List<ChannelApiContext> channelApiContexts = new ArrayList<>();
            channelHolders.sort(Comparator.comparing(ChannelHolder::getScore));
            channelHolders.forEach(channelHolder -> {
                ChannelApiHolder resultChannelApi = channelHolder.getChannelApiHolder();
                channelApiContexts.add(buildChannelApiContext(resultChannelApi.getChannelApi(), resultChannelApi.getChannelFullInfo()));
            });
            return channelApiContexts;
        }
        return null;
    }

    @Override
    public ChannelApiContext routeByChannel(String channelCode, ChannelApiType apiType) {
        ChannelFullInfo channelFullInfo = channelFullInfoRepository.getChannelFullInfo(channelCode);
        if (channelFullInfo != null) {
            List<ChannelApi> channelApis = channelFullInfo.getChannelApis();
            for (ChannelApi channelApi : channelApis) {
                if (channelApi.getType() == apiType) {
                    return buildChannelApiContext(channelApi, channelFullInfo);
                }
            }
        }
        return null;
    }

    private void fillChannelHolder(RouteParam routeParam, ChannelFullInfo channelFullInfo, List<ChannelHolder> channelHolders, int score) {
        ChannelApiHolder channelApiHolder = routeChannelApi(channelFullInfo, routeParam);
        if (channelApiHolder != null) {
            channelHolders.add(new ChannelHolder(channelApiHolder, score));
        }
    }

    private ChannelApiContext buildChannelApiContext(ChannelApi channelApi, ChannelFullInfo channelFullInfo) {
        ChannelApiContext channelApiContext = new ChannelApiContext();
        channelApiContext.setChannel(channelFullInfo.getFundChannel());
        channelApiContext.setChannelApi(channelApi);
        channelApiContext.setMaintain(false);
        return channelApiContext;
    }

    /**
     * 对渠道进行打分，0分的不可用
     *
     * @param channelFullInfo
     * @param routeParam
     * @return
     */
    private int scoreChannel(ChannelFullInfo channelFullInfo, RouteParam routeParam) {
        int totalScore = RouteScorer.ZERO_SCORE;
        for (ChannelScorer scorer : channelScorers) {
            int score = scorer.score(channelFullInfo, routeParam);
            if (score > RouteScorer.ZERO_SCORE) {
                totalScore += score;
            } else {
                return RouteScorer.ZERO_SCORE;
            }
        }
        return totalScore;
    }

    /**
     * 路由渠道API，筛选可用的
     *
     * @param channelFullInfo
     * @param routeParam
     * @return
     */
    private ChannelApiHolder routeChannelApi(ChannelFullInfo channelFullInfo, RouteParam routeParam) {
        List<ChannelApiHolder> channelApiHolders = new ArrayList<>();
        channelFullInfo.getChannelApis().forEach(channelApi -> {
            int score = scoreChannelApi(channelApi, channelFullInfo, routeParam);
            if (score > RouteScorer.ZERO_SCORE) {
                channelApiHolders.add(new ChannelApiHolder(channelApi, channelFullInfo, score));
            }
        });
        if (CollectionUtil.isNotEmpty(channelApiHolders)) {
            channelApiHolders.sort(Comparator.comparing(ChannelApiHolder::getScore));
            return channelApiHolders.get(0);
        }
        return null;
    }

    /**
     * 渠道API打分，对渠道API进行打分，0分的不可用
     *
     * @param channelApi
     * @param channelFullInfo
     * @param routeParam
     * @return
     */
    private int scoreChannelApi(ChannelApi channelApi, ChannelFullInfo channelFullInfo, RouteParam routeParam) {
        int totalScore = RouteScorer.ZERO_SCORE;
        if (CollectionUtil.isNotEmpty(channelApiScorers)) {
            for (ChannelApiScorer scorer : channelApiScorers) {
                int score = scorer.score(channelApi, channelFullInfo, routeParam);
                if (score > RouteScorer.ZERO_SCORE) {
                    totalScore += score;
                } else {
                    return RouteScorer.ZERO_SCORE;
                }
            }
        }
        return totalScore;
    }

    @Data
    @AllArgsConstructor
    private static class ChannelApiHolder {

        /**
         * API
         */
        private ChannelApi channelApi;

        /**
         * 渠道API
         */
        private ChannelFullInfo channelFullInfo;

        /**
         * 分数
         */
        private int score;
    }

    @Data
    @AllArgsConstructor
    private static class ChannelHolder {

        /**
         * API
         */
        private ChannelApiHolder channelApiHolder;

        /**
         * 分数
         */
        private int score;
    }

}
