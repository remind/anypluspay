package com.anypluspay.payment.infra.message.producer;

import com.anypluspay.payment.application.message.PayOrderResultProducer;
import com.anypluspay.payment.types.message.PayOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2025/4/10
 */
@Component
@Slf4j
public class PayOrderResultProducerImpl implements PayOrderResultProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.producer.payment-order-result}")
    private String topic;

    @Override
    public void send(PayOrderResult payOrderResult) {
        log.info("发送支付订单结果消息:{}", payOrderResult);
        rocketMQTemplate.convertAndSend(topic, payOrderResult);
    }
}
