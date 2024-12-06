package com.anypluspay.channel.facade.builder;

import com.anypluspay.channel.domain.IdType;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.InstProcessOrder;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.InstProcessOrderStatus;
import com.anypluspay.component.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2024/8/13
 */
@Component
public class InstProcessOrderBuilder {

    @Autowired
    private SequenceService sequenceService;

    public InstProcessOrder build(ChannelApiContext channelApiContext, InstOrder instOrder) {
        InstProcessOrder instProcessOrder = new InstProcessOrder();
        instProcessOrder.setInstProcessId(sequenceService.getIdByRelateId(instOrder.getInstOrderId(), IdType.INST_PROCESS_ORDER));
        instProcessOrder.setInstOrderId(instOrder.getInstOrderId());
        instProcessOrder.setFundChannelCode(instOrder.getFundChannelCode());
        instProcessOrder.setApiType(channelApiContext.getChannelApiType());
        instProcessOrder.setStatus(InstProcessOrderStatus.PROCESSING);
        return instProcessOrder;
    }

    public InstProcessOrder buildManual(InstProcessOrder instProcessOrder, ChannelApiType manualApiType) {
        InstProcessOrder manualProcessOrder = new InstProcessOrder();
        manualProcessOrder.setInstProcessId(sequenceService.getIdByRelateId(instProcessOrder.getInstOrderId(), IdType.INST_PROCESS_ORDER));
        manualProcessOrder.setInstOrderId(instProcessOrder.getInstOrderId());
        manualProcessOrder.setFundChannelCode(instProcessOrder.getFundChannelCode());
        manualProcessOrder.setApiType(manualApiType);
        manualProcessOrder.setStatus(InstProcessOrderStatus.PROCESSING);
        return manualProcessOrder;
    }


}
