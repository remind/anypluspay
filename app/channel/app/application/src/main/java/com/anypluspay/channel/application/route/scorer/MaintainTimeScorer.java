package com.anypluspay.channel.application.route.scorer;

import cn.hutool.core.collection.CollectionUtil;
import com.anypluspay.channel.application.route.RouteParam;
import com.anypluspay.channel.application.route.maintain.MaintainCheck;
import com.anypluspay.channel.domain.channel.ChannelFullInfo;
import com.anypluspay.channel.domain.channel.ChannelMaintain;
import com.anypluspay.channel.types.channel.MaintainTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 维护期打分，满足就为5分，否则为0分
 *
 * @author wxj
 * 2024/12/3
 */
@Service
public class MaintainTimeScorer implements ChannelScorer {

    @Autowired
    private List<MaintainCheck> maintainChecks;

    @Override
    public int score(ChannelFullInfo channelFullInfo, RouteParam routeParam) {
        List<ChannelMaintain> channelMaintains = channelFullInfo.getChannelMaintains();
        if (CollectionUtil.isNotEmpty(channelMaintains)) {
            for (ChannelMaintain channelMaintain : channelMaintains) {
                if (isMaintain(channelMaintain.getMaintainTimeType(), channelMaintain.getTimeRange())) {
                    return ZERO_SCORE;
                }
            }
        }
        return STANDARD_SCORE;
    }

    private boolean isMaintain(MaintainTimeType type, String timeRange) {
        for (MaintainCheck maintainCheck : maintainChecks) {
            if (maintainCheck.support(type)) {
                return maintainCheck.check(LocalDateTime.now(), timeRange);
            }
        }
        return false;
    }
}
