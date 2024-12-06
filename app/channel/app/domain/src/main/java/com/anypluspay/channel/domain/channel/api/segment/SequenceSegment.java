package com.anypluspay.channel.domain.channel.api.segment;

/**
 * 序列片段
 * @author wxj
 * 2024/9/18
 */
public class SequenceSegment implements Segment {

    /**
     * 长度
     */
    private final int length;
    /**
     * 流水号
     */
    private final long sequence;

    public SequenceSegment(String length, long sequence) {
        this.length = Integer.parseInt(length);
        this.sequence = sequence;
    }

    @Override
    public String get() {
        return getSegment(sequence);
    }

    private String getSegment(long seq) {
        return String.format("%0" + this.length + "d", seq);
    }
}
