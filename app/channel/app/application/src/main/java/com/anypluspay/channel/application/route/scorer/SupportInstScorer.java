package com.anypluspay.channel.application.route.scorer;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.anypluspay.channel.application.route.FieldMatcher;
import com.anypluspay.channel.application.route.RouteParam;
import com.anypluspay.channel.domain.channel.ChannelFullInfo;
import com.anypluspay.channel.domain.channel.ChannelSupportInst;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支持的机构打分，满足就为5分，否则为0分
 *
 * @author wxj
 * 2024/6/28
 */
@Service
public class SupportInstScorer implements ChannelScorer {
    @Override
    public int score(ChannelFullInfo channelFullInfo, RouteParam routeParam) {
        List<ChannelSupportInst> channelSupportInstList = channelFullInfo.getChannelSupportInst();
        if (CollectionUtil.isNotEmpty(channelSupportInstList)) {
            for (ChannelSupportInst channelSupportInst : channelSupportInstList) {
                boolean ret = match(routeParam, channelSupportInst);
                if (ret) {
                    return STANDARD_SCORE;
                }
            }
        }
        return ZERO_SCORE;
    }

    /**
     * 验证单条机构
     *
     * @param routeParam
     * @param channelSupportInst
     * @return
     */
    private boolean match(RouteParam routeParam, ChannelSupportInst channelSupportInst) {
        Assert.notNull(routeParam.getPayInst(), "支付机构不能为空");
        if (FieldMatcher.matcherScore(routeParam.getPayInst(), channelSupportInst.getTargetInstCode()) == ZERO_SCORE) {
            return false;
        }
        if (FieldMatcher.matcherScore(routeParam.getCardType(), channelSupportInst.getCardType()) == ZERO_SCORE) {
            return false;
        }
        if (!matchByPerAmount(routeParam, channelSupportInst)) {
            return false;
        }
        return matchByExtra(routeParam, channelSupportInst);
    }

    /**
     * 单笔金额范围
     *
     * @param routeParam
     * @param channelSupportInst
     * @return
     */
    private boolean matchByPerAmount(RouteParam routeParam, ChannelSupportInst channelSupportInst) {
        return channelSupportInst.getPerAmountRange() == null || channelSupportInst.getPerAmountRange().contains(routeParam.getAmount().getAmount());
    }

    /**
     * 扩展字段匹配
     * @param routeParam
     * @param channelSupportInst
     * @return
     */
    private boolean matchByExtra(RouteParam routeParam, ChannelSupportInst channelSupportInst) {
        if (StrUtil.isBlank(channelSupportInst.getExtra())) {
            return true;
        }
        if (routeParam.getExtra() == null) {
            return false;
        }
        Map<String, String> extraMap = parseStringToMap(channelSupportInst.getExtra());
        for (Map.Entry<String, String> entry : extraMap.entrySet()) {
            if (!entry.getValue().equals(routeParam.getExtra().get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将字符串解析为键值对
     * @param input a=1&b=2
     * @return
     */
    public static Map<String, String> parseStringToMap(String input) {
        Map<String, String> map = new HashMap<>();
        if (input != null && !input.isEmpty()) {
            // 使用 '&' 分割字符串
            String[] pairs = input.split("&");
            for (String pair : pairs) {
                // 使用 '=' 分割键值对
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    map.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return map;
    }
}
