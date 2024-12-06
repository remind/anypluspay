package com.anypluspay.channel.domain.channel.api.segment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间片段
 *
 * @author wxj
 * 2024/9/18
 */
public class TimeSegment implements Segment {
    private final String format;

    public TimeSegment(String format) {
        this.format = format;
    }

    @Override
    public String get() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(this.format);
        return dtf.format(LocalDateTime.now());
    }

}
