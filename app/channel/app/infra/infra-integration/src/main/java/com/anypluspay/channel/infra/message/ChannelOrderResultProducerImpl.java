package com.anypluspay.channel.infra.message;

import com.anypluspay.channel.application.message.ChannelOrderResultProducer;
import com.anypluspay.channel.facade.result.FundResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 渠道订单结果消息发送
 *
 * @author wxj
 * 2025/4/9
 */
@Component
@Slf4j
public class ChannelOrderResultProducerImpl implements ChannelOrderResultProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.producer.channel-order-result}")
    private String topic;

    @Override
    public void send(FundResult fundResult) {
        log.info("发送渠道订单结果消息:{}", fundResult);
        rocketMQTemplate.convertAndSend(topic, fundResult);
    }
}
