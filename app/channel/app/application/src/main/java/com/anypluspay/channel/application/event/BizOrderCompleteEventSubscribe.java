package com.anypluspay.channel.application.event;

import com.anypluspay.channel.application.message.ChannelOrderResultProducer;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.facade.fund.builder.FundResultBuilder;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 渠道业务订单处理完成事件订阅
 *
 * @author wxj
 * 2025/4/9
 */
@Slf4j
@Component
public class BizOrderCompleteEventSubscribe {

    @Autowired
    private ChannelOrderResultProducer channelOrderResultProducer;

    @Autowired
    private FundResultBuilder fundResultBuilder;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(BizOrderCompleteEvent bizOrderCompleteEvent) {
        OrderContext orderContext = bizOrderCompleteEvent.getOrderContext();
        if (orderContext.getBizOrder().getStatus() == BizOrderStatus.SUCCESS
                || orderContext.getBizOrder().getStatus() == BizOrderStatus.FAILED) {
            // 终态发送结果消息
            FundResult fundResult = fundResultBuilder.buildFundInResult(orderContext.getBizOrder(), orderContext.getInstOrder(), orderContext.getInstCommandOrder());
            channelOrderResultProducer.send(fundResult);
        }
    }
}
