package com.anypluspay.channel.facade.builder;

import com.anypluspay.channel.domain.IdType;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.service.BizOrderService;
import com.anypluspay.channel.domain.channel.service.SubmitInstStrategy;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.types.enums.TaskStatus;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.order.SubmitTimeType;
import com.anypluspay.component.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/8/16
 */
@Component
public class InstOrderBuilder {

    @Autowired
    private BizOrderService bizOrderService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private SubmitInstStrategy submitInstStrategy;

    public InstOrder buildInstOrder(ChannelApiContext channelApiContext, BaseBizOrder bizOrder) {
        InstOrder instOrder = new InstOrder();
        instOrder.setBizOrderId(bizOrder.getOrderId());
        instOrder.setInstRequestNo(bizOrderService.genInstRequestNo(channelApiContext));
        instOrder.setFundChannelCode(channelApiContext.getChannelCode());
        instOrder.setApiType(channelApiContext.getChannelApiType());
        instOrder.setRequestExtra(bizOrder.getInstExtra());
        instOrder.setSubmitTimeType(submitInstStrategy.getSubmitTimeType(channelApiContext));
        instOrder.setBookSubmitTime(LocalDateTime.now());
        if (instOrder.getSubmitTimeType() == SubmitTimeType.REAL) {
            instOrder.setSubmitTime(instOrder.getBookSubmitTime());
            instOrder.setStatus(InstOrderStatus.PROCESSING);
            instOrder.setNextRetryTime(submitInstStrategy.calculateNextRetryTime(instOrder.getSubmitTime()));
        } else {
            instOrder.setStatus(InstOrderStatus.INIT);
        }
        instOrder.setTaskStatus(TaskStatus.UNLOCK);
        return instOrder;
    }
}
