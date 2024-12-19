package com.anypluspay.channel.infra.schedule;

import com.anypluspay.channel.application.institution.InstProcessService;
import com.anypluspay.channel.application.route.ChannelRouteService;
import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.bizorder.OrderContext;
import com.anypluspay.channel.domain.channel.api.service.ChannelApiDomainService;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.infra.persistence.mapper.TaskQuery;
import com.anypluspay.channel.types.enums.TaskStatus;
import com.anypluspay.channel.types.order.InstOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 机构订单查询补单任务
 *
 * @author wxj
 * 2024/12/13
 */
@Service
public class InstOrderQueryTask extends AbstractInstOrderTask {

    @Autowired
    protected ChannelRouteService channelRouteService;

    @Autowired
    protected InstProcessService instProcessService;

    @Autowired
    protected InstOrderRepository instOrderRepository;

    @Autowired
    protected ChannelApiDomainService channelApiDomainService;

    @Autowired
    protected BizOrderRepository bizOrderRepository;

    @Scheduled(cron = "0/3 * * * * *")
    @Override
    public void executeTask() {
        super.executeTask();
    }

    @Override
    List<Long> loadInstOrder() {
        TaskQuery query = new TaskQuery();
        query.setSize(QUERY_SIZE);
        query.setStatus(InstOrderStatus.PROCESSING.getCode());
        query.setTaskStatus(TaskStatus.UNLOCK.getCode());
        return taskLoaderMapper.loadInstOrderByQueryTask(query);
    }

    @Override
    InstOrder handleInstOrder(Long instOrderId) {
        InstOrder instOrder = process(instOrderId);
        LocalDateTime nextRetryTime = null;
        if (instOrder.getStatus() == InstOrderStatus.PROCESSING) {
            nextRetryTime = LocalDateTime.now().plusMinutes(5);
        }
        releaseLock(instOrderId, nextRetryTime);
        return instOrder;
    }

    @Override
    LocalDateTime getNextRetryTime() {
        return null;
    }

    private InstOrder process(Long instOrderId) {
        InstOrder instOrder = instOrderRepository.load(instOrderId);
        ChannelApiContext channelApiContext = channelRouteService.routeByChannel(instOrder.getFundChannelCode(), channelApiDomainService.getQueryApiType(instOrder.getApiType()));
        BaseBizOrder bizOrder = bizOrderRepository.load(instOrder.getBizOrderId());
        OrderContext orderContext = instProcessService.createAndSubmit(channelApiContext, bizOrder, instOrder);
        return orderContext.getInstOrder();
    }
}
