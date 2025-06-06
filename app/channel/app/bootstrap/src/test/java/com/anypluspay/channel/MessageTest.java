package com.anypluspay.channel;

import com.anypluspay.channel.base.AbstractBaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wxj
 * 2025/4/8
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MessageTest extends AbstractBaseTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.producer.payment-flux-result-topic}")
    private String topic;

    @Test
    public void sendMessage() {
        rocketMQTemplate.convertAndSend(topic, "Hello, World!");
    }
}
