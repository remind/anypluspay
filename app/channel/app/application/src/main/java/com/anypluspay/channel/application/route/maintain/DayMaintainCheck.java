package com.anypluspay.channel.application.route.maintain;

import com.anypluspay.channel.types.channel.MaintainTimeType;

import java.time.LocalDateTime;

/**
 * 每天型检查
 * @author wxj
 * 2024/12/3
 */
public class DayMaintainCheck implements MaintainCheck{
    @Override
    public boolean support(MaintainTimeType type) {
        return type == MaintainTimeType.DAY;
    }

    @Override
    public boolean check(LocalDateTime time, String timeRange) {
        return false;
    }

}
