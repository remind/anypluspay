package com.anypluspay.channel.facade.builder;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.service.BizOrderService;
import com.anypluspay.channel.domain.channel.api.ChannelApiParam;
import com.anypluspay.channel.domain.channel.service.SubmitInstStrategy;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.repository.channel.ChannelApiParamRepository;
import com.anypluspay.channel.types.enums.TaskStatus;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.order.SubmitTimeType;
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
    private SubmitInstStrategy submitInstStrategy;

    @Autowired
    private ChannelApiParamRepository channelApiParamRepository;

    public InstOrder buildInstOrder(ChannelApiContext channelApiContext, BaseBizOrder bizOrder) {
        InstOrder instOrder = new InstOrder();
        instOrder.setBizOrderId(bizOrder.getOrderId());
        instOrder.setInstRequestNo(bizOrderService.genInstRequestNo(channelApiContext));
        instOrder.setFundChannelCode(channelApiContext.getChannelCode());
        instOrder.setApiType(channelApiContext.getChannelApiType());
        instOrder.setRequestExt(bizOrder.getInstExt());
        instOrder.setSubmitTimeType(submitInstStrategy.getSubmitTimeType(channelApiContext));
        instOrder.setBookSubmitTime(LocalDateTime.now());
        if (instOrder.getSubmitTimeType() == SubmitTimeType.REAL) {
            instOrder.setSubmitTime(instOrder.getBookSubmitTime());
            instOrder.setStatus(InstOrderStatus.PROCESSING);
            instOrder.setNextRetryTime(submitInstStrategy.calculateNextRetryTime(instOrder.getSubmitTime()));
        } else {
            instOrder.setStatus(InstOrderStatus.INIT);
        }
        instOrder.setApiParamId(getApiParamId(bizOrder.getPartnerId(), channelApiContext.getChannelCode()));
        instOrder.setTaskStatus(TaskStatus.UNLOCK);
        return instOrder;
    }

    private String getApiParamId(String partnerId, String channelCode) {
        ChannelApiParam channelApiParam = channelApiParamRepository.loadByPartnerIdANdChannelCode(partnerId, channelCode);
        return channelApiParam == null ? null : channelApiParam.getId();
    }
}
