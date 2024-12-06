package com.anypluspay.channel.domain.channel.api.segment;

/**
 * 固定片段
 * @author wxj
 * 2024/9/18
 */
public class FixedSegment implements Segment {

    private final String fixed;

    public FixedSegment(String fixed) {
        this.fixed = fixed;
    }

    @Override
    public String get() {
        return this.fixed;
    }
}
