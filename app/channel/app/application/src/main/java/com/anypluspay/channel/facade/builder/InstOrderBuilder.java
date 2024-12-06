package com.anypluspay.channel.facade.builder;

import com.anypluspay.channel.domain.IdType;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.service.BizOrderService;
import com.anypluspay.channel.domain.institution.InstDelayOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.types.order.DelayOrderStatus;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.order.ProcessTimeType;
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

    public InstOrder buildInstOrder(ChannelApiContext channelApiContext, BaseBizOrder bizOrder) {
        InstOrder instOrder = new InstOrder();
        instOrder.setBizOrderId(bizOrder.getOrderId());
        instOrder.setInstOrderId(sequenceService.getIdByRelateId(bizOrder.getOrderId(), IdType.INST_ORDER));
        instOrder.setInstRequestNo(bizOrderService.genInstRequestNo(channelApiContext));
        instOrder.setFundChannelCode(channelApiContext.getChannelCode());
        instOrder.setApiType(channelApiContext.getChannelApiType());
        instOrder.setRequestExtra(bizOrder.getInstExtra());

        fillSubmitTimeType(channelApiContext, instOrder);

        if (instOrder.getProcessTimeType() == ProcessTimeType.REAL) {
            instOrder.setProcessTime(LocalDateTime.now());
            instOrder.setStatus(InstOrderStatus.PROCESSING);
        } else {
            instOrder.setStatus(InstOrderStatus.INIT);
        }

        return instOrder;
    }

    private void fillSubmitTimeType(ChannelApiContext channelApiContext, InstOrder instOrder) {
        switch (channelApiContext.getChannelApiType()) {
            case SINGLE_FUND_OUT:
                instOrder.setProcessTimeType(ProcessTimeType.DELAYED);
                instOrder.setInstDelayOrder(buildDelayOrder(instOrder, LocalDateTime.now()));
            default:
                instOrder.setProcessTimeType(ProcessTimeType.REAL);
        }
    }

    private InstDelayOrder buildDelayOrder(InstOrder instOrder, LocalDateTime submitBookTime) {
        InstDelayOrder instDelayOrder = new InstDelayOrder();
        instDelayOrder.setInstOrderId(instOrder.getInstOrderId());
        instDelayOrder.setStatus(DelayOrderStatus.WAIT);
        instDelayOrder.setBookProcessTime(submitBookTime);
        instDelayOrder.setCount(0);
        return instDelayOrder;
    }
}
