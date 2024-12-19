package com.anypluspay.channel.domain.channel.service;

import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.SubmitTimeType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 提交机构策略
 *
 * @author wxj
 * 2024/12/13
 */
@Service
public class SubmitInstStrategy {

    /**
     * 计算下次补单查询时间
     *
     * @param submitTime
     * @return
     */
    public LocalDateTime calculateNextRetryTime(LocalDateTime submitTime) {
        return submitTime.plusMinutes(10);
    }

    /**
     * 获取订单提交时间类型
     *
     * @param channelApiContext
     * @return
     */
    public SubmitTimeType getSubmitTimeType(ChannelApiContext channelApiContext) {
        if (Objects.requireNonNull(channelApiContext.getChannelApiType()) == ChannelApiType.SINGLE_FUND_OUT) {
            return SubmitTimeType.DELAYED;
        }
        return SubmitTimeType.REAL;
    }
}
