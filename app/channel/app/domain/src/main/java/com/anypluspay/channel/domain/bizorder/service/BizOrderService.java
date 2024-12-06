package com.anypluspay.channel.domain.bizorder.service;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.channel.api.service.ApiRequestNoModeService;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.types.order.BizOrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务单据领域服务
 * @author wxj
 * 2024/7/13
 */
@Service
@Slf4j
public class BizOrderService {

    @Autowired
    private BizOrderRepository bizOrderRepository;

    @Autowired
    private ApiRequestNoModeService apiRequestNoModeService;

    public void updateStatus(BaseBizOrder bizOrder, InstOrder instOrder) {
        switch (instOrder.getStatus()) {
            case SUCCESS -> bizOrder.setStatus(BizOrderStatus.SUCCESS);
            case FAILED -> bizOrder.setStatus(BizOrderStatus.FAILED);
            default -> bizOrder.setStatus(BizOrderStatus.PROCESSING);
        }
        bizOrderRepository.reStore(bizOrder);
    }

    public void relateInstOrderId(BaseBizOrder bizOrder, String instOrderId) {
        bizOrder.setInstOrderId(instOrderId);
        bizOrderRepository.reStore(bizOrder);
    }

    public String genInstRequestNo(ChannelApiContext channelApiContext) {
        return apiRequestNoModeService.generateRequestNo(channelApiContext.getChannelApi().getApiRequestNoMode());
    }
}
