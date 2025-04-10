package com.anypluspay.testtrade.message;

import com.anypluspay.payment.types.message.PayOrderResult;
import com.anypluspay.testtrade.service.PayService;
import com.anypluspay.testtrade.types.PayStatus;
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
@RocketMQMessageListener(nameServer = "${rocketmq.name-server}", topic = "${rocketmq.consumer.payment-order-result.topic}", consumerGroup = "${rocketmq.consumer.payment-order-result.group}")
public class PayOrderResultConsumer implements RocketMQListener<PayOrderResult> {

    @Autowired
    private PayService payService;
    @Override
    public void onMessage(PayOrderResult message) {
        log.info("收到支付订单结果消息:{}", message);
        payService.processResult(Long.valueOf(message.getRequestId()), "SUCCESS".equals(message.getStatus()) ? PayStatus.SUCCESS : PayStatus.FAIL);
    }
}
