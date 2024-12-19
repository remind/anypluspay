package com.anypluspay.channel.infra.schedule;

import com.anypluspay.channel.application.institution.DelayOrderProcessService;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.infra.persistence.mapper.TaskQuery;
import com.anypluspay.channel.types.enums.TaskStatus;
import com.anypluspay.channel.types.order.InstOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 延迟提交订单处理任务
 *
 * @author wxj
 * 2024/12/13
 */
@Service
public class DelayOrderProcessTask extends AbstractInstOrderTask {

    @Autowired
    private DelayOrderProcessService delayOrderProcessService;

    @Scheduled(cron = "0/10 * * * * *")
    @Override
    public void executeTask() {
        super.executeTask();
    }

    @Override
    List<Long> loadInstOrder() {
        TaskQuery query = new TaskQuery();
        query.setSize(QUERY_SIZE);
        query.setStatus(InstOrderStatus.INIT.getCode());
        query.setTaskStatus(TaskStatus.UNLOCK.getCode());
        return taskLoaderMapper.loadDelayInstOrder(query);
    }

    @Override
    InstOrder handleInstOrder(Long instOrderId) {
        return delayOrderProcessService.process(instOrderId);
    }

    @Override
    LocalDateTime getNextRetryTime() {
        return null;
    }
}
