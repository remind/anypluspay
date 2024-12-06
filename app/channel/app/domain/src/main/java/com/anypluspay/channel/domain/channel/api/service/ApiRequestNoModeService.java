package com.anypluspay.channel.domain.channel.api.service;

import com.anypluspay.channel.domain.channel.api.ApiRequestNoMode;
import com.anypluspay.channel.domain.channel.api.segment.FixedSegment;
import com.anypluspay.channel.domain.channel.api.segment.Segment;
import com.anypluspay.channel.domain.channel.api.segment.SequenceSegment;
import com.anypluspay.channel.domain.channel.api.segment.TimeSegment;
import com.anypluspay.component.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * API 请求号生成模式服务
 * @author wxj
 * 2024/9/18
 */
@Service
public class ApiRequestNoModeService {

    private static final String FIXED = "f";
    private static final String TIME = "t";
    private static final String SEQUENCE = "s";

    @Autowired
    private SequenceService sequenceService;

    public String generateRequestNo(ApiRequestNoMode apiRequestNoMode) {
        List<Segment> segments = getSegmentList(apiRequestNoMode);
        StringBuilder id = new StringBuilder();
        for (Segment segment : segments) {
            id.append(segment.get());
        }
        return id.toString();
    }

    private List<Segment> getSegmentList(ApiRequestNoMode apiRequestNoMode) {
        List<Segment> result = new ArrayList<>();
        String genPattern = apiRequestNoMode.getGenPattern();
        String[] segments = genPattern.split("\\|");
        for (String segment : segments) {
            String[] fields = segment.split(":");
            Segment s = getSegment(fields, apiRequestNoMode.getSeqName());
            result.add(s);
        }
        return result;
    }

    private Segment getSegment(String[] fields, String seqName) {
        String prefix = fields[0];
        String length = fields[1];
        if (FIXED.equals(prefix)) {
            return new FixedSegment(length);
        } else if (TIME.equals(prefix)) {
            return new TimeSegment(length);
        } else if (SEQUENCE.equals(prefix)) {
            return new SequenceSegment(length, sequenceService.getNext(seqName));
        }
        return new FixedSegment("");
    }
}
