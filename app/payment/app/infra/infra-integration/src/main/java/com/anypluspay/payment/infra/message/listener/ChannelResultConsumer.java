package com.anypluspay.payment.infra.message.listener;

import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.payment.application.notify.PayNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2025/4/10
 */
@Component
@Slf4j
@RocketMQMessageListener(nameServer = "${rocketmq.name-server}", topic = "${rocketmq.consumer.channel-order-result.topic}", consumerGroup = "${rocketmq.consumer.channel-order-result.group}")
public class ChannelResultConsumer implements RocketMQListener<FundResult> {

    @Autowired
    private PayNotifyService payNotifyService;

    @Override
    public void onMessage(FundResult message) {
        log.info("收到到渠道消息：{}", message);
        try {
            payNotifyService.process(message);
        } catch (Exception e) {
            log.error("处理渠道订单结果消息异常", e);
        }
    }
}
