package com.anypluspay.channel.application.message;

import com.anypluspay.channel.facade.result.FundResult;

/**
 * 渠道订单结果消息
 * @author wxj
 * 2025/4/9
 */
public interface ChannelOrderResultProducer {

    /**
     * 发送渠道订单结果消息
     * @param fundResult  订单上下文
     */
    void send(FundResult fundResult);
}
