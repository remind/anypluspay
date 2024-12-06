package com.anypluspay.channel.application.route.maintain;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.channel.types.channel.MaintainTimeType;

import java.time.LocalDateTime;

/**
 * 维护期检查
 * @author wxj
 * 2024/12/3
 */
public interface MaintainCheck {

    /**
     * 支持的类型
     * @param type
     * @return
     */
    boolean support(MaintainTimeType type);

    /**
     * 检查指定时间是否在时间区间内
     * @param time  时间
     * @param timeRange 时间区间
     * @return
     */
    boolean check(LocalDateTime time, String timeRange);

    default String[] splitTimeRange(String timeRange) {
        if (StrUtil.isBlank(timeRange)) {
            return null;
        }
        String[] timeRangeArr = timeRange.split(",");
        if (timeRangeArr.length != 2) {
            return null;
        }
        return timeRangeArr;
    }
}
