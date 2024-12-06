package com.anypluspay.channel.application.institution;

import com.anypluspay.channel.application.route.ChannelRouteService;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.institution.InstDelayOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.types.order.DelayOrderStatus;
import com.anypluspay.channel.types.order.InstOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;

/**
 * 延迟订单处理
 *
 * @author wxj
 * 2024/8/16
 */
public class InstDelayProcessService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private BizOrderRepository bizOrderRepository;

    @Autowired
    private InstOrderRepository instOrderRepository;

    @Autowired
    private InstProcessService instProcessService;

    @Autowired
    private ChannelRouteService channelRouteService;

    public void process(String instOrderId) {
        if (lockProcessOrder(instOrderId)) {
            InstOrder instOrder = instOrderRepository.load(instOrderId);
            BaseBizOrder baseBizOrder = bizOrderRepository.load(instOrder.getBizOrderId());
            ChannelApiContext channelApiContext = channelRouteService.routeByChannel(instOrder.getFundChannelCode(), instOrder.getApiType());
            instProcessService.process(channelApiContext, baseBizOrder, instOrder);
            finishProcessOrder(instOrderId);
        }
    }

    private boolean lockProcessOrder(String instOrderId) {
        return Boolean.TRUE.equals(transactionTemplate.execute(transactionStatus -> {
            InstOrder instOrder = instOrderRepository.load(instOrderId);
            if (instOrder.getStatus() == InstOrderStatus.INIT) {
                InstDelayOrder instDelayOrder = instOrder.getInstDelayOrder();
                if (instDelayOrder.getStatus() == DelayOrderStatus.WAIT
                        || instDelayOrder.getStatus() == DelayOrderStatus.LOCK && instDelayOrder.getLastProcessTime().plusMinutes(30).isAfter(LocalDateTime.now())
                ) {
                    instDelayOrder.setStatus(DelayOrderStatus.LOCK);
                    instDelayOrder.setLastProcessTime(LocalDateTime.now());
                    instDelayOrder.setCount(instDelayOrder.getCount() + 1);
                    instOrderRepository.updateDelayOrder(instDelayOrder);
                    return true;
                }
            }
            return false;
        }));
    }

    private void finishProcessOrder(String instProcessId) {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            InstOrder instOrder = instOrderRepository.lock(instProcessId);
            InstDelayOrder instDelayOrder = instOrder.getInstDelayOrder();
            instDelayOrder.setStatus(DelayOrderStatus.FINISH);
            instOrderRepository.updateDelayOrder(instDelayOrder);
        });
    }

}
