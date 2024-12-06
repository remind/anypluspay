package com.anypluspay.channel.application.route.maintain;

import com.anypluspay.channel.types.channel.MaintainTimeType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 固定时间型检查
 *
 * @author wxj
 * 2024/12/3
 */
@Component
public class FixedMaintainCheck implements MaintainCheck {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean support(MaintainTimeType type) {
        return type == MaintainTimeType.FIXED;
    }

    @Override
    public boolean check(LocalDateTime time, String timeRange) {
        String[] timeRangeArr = splitTimeRange(timeRange);
        if (timeRangeArr == null) {
            return false;
        }
        LocalDateTime startTime = LocalDateTime.parse(timeRangeArr[0], dtf);
        LocalDateTime endTime = LocalDateTime.parse(timeRangeArr[1], dtf);
        return LocalDateTime.now().isAfter(startTime) && LocalDateTime.now().isBefore(endTime);
    }

}
