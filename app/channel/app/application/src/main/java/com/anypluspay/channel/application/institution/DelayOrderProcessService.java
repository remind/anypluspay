package com.anypluspay.channel.application.institution;

import com.anypluspay.channel.application.route.ChannelRouteService;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 延迟订单处理
 *
 * @author wxj
 * 2024/8/16
 */
@Service
public class DelayOrderProcessService {

    @Autowired
    private BizOrderRepository bizOrderRepository;

    @Autowired
    private InstOrderRepository instOrderRepository;

    @Autowired
    private InstProcessService instProcessService;

    @Autowired
    private ChannelRouteService channelRouteService;

    public InstOrder process(Long instOrderId) {
        InstOrder instOrder = instOrderRepository.load(instOrderId);
        BaseBizOrder baseBizOrder = bizOrderRepository.load(instOrder.getBizOrderId());
        ChannelApiContext channelApiContext = channelRouteService.routeByChannel(instOrder.getFundChannelCode(), instOrder.getApiType());
        OrderContext result = instProcessService.createAndSubmit(channelApiContext, baseBizOrder, instOrder);
        return result.getInstOrder();
    }

}
