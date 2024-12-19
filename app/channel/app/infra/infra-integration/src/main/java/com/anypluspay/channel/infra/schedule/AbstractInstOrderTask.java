package com.anypluspay.channel.infra.schedule;

import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.infra.persistence.mapper.TaskLoaderMapper;
import com.anypluspay.channel.types.enums.TaskStatus;
import com.anypluspay.channel.types.order.InstOrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

/**
 * @author wxj
 * 2024/12/13
 */
@Slf4j
public abstract class AbstractInstOrderTask {

    @Autowired
    protected TaskLoaderMapper taskLoaderMapper;

    @Autowired
    protected Executor taskExecutor;

    /**
     * 每次查询的数量
     */
    protected static final int QUERY_SIZE = 100;

    /**
     * 执行任务
     */
    public void executeTask() {
        List<Long> instOrderIds = loadInstOrder();
        while (!CollectionUtils.isEmpty(instOrderIds)) {
            Map<Long, FutureTask<InstOrder>> futureMap = new HashMap<>();
            instOrderIds.forEach(instOrderId -> {
                int flag = taskLoaderMapper.tryLock(instOrderId, TaskStatus.LOCK.getCode(), TaskStatus.UNLOCK.getCode());
                if (flag == 1) {
                    FutureTask<InstOrder> futureTask = new FutureTask<>(
                            new FutureTaskCallable(instOrderId));
                    futureMap.put(instOrderId, futureTask);
                    taskExecutor.execute(futureTask);
                }
            });
            instOrderIds = loadInstOrder();
            handleResult(futureMap);
        }
    }

    /**
     * 结果处理
     *
     * @param futureMap
     */
    private void handleResult(Map<Long, FutureTask<InstOrder>> futureMap) {
        for (Map.Entry<Long, FutureTask<InstOrder>> entry : futureMap.entrySet()) {
            LocalDateTime retryTime = getNextRetryTime();
            try {
                InstOrder result = entry.getValue().get();
                if (result.getStatus() == InstOrderStatus.SUCCESS || result.getStatus() == InstOrderStatus.FAILED) {
                    retryTime = null;
                }
                log.info("机构订单任务，instOrderId：{}，任务执行结果：{}", entry.getKey(), result.getStatus());
            } catch (Exception e) {
                log.error("机构订单任务，instOrderId：{}，任务执行异常", entry.getKey(), e);
            } finally {
                releaseLock(entry.getKey(), retryTime);
            }
        }
    }

    /**
     * 释放锁
     *
     * @param instOrderId
     * @param nextRetryTime
     */
    protected void releaseLock(Long instOrderId, LocalDateTime nextRetryTime) {
        taskLoaderMapper.releaseLock(instOrderId, TaskStatus.LOCK.getCode(), TaskStatus.UNLOCK.getCode(), nextRetryTime);
    }

    /**
     * 加载需要处理的订单
     *
     * @return
     */
    abstract List<Long> loadInstOrder();

    /**
     * 处理订单
     *
     * @param instOrderId
     */
    abstract InstOrder handleInstOrder(Long instOrderId);

    /**
     * 获取下一次重试时间
     *
     * @return
     */
    abstract LocalDateTime getNextRetryTime();

    private class FutureTaskCallable implements Callable<InstOrder> {

        private final Long instOrderId;

        public FutureTaskCallable(Long instOrderId) {
            this.instOrderId = instOrderId;
        }

        @Override
        public InstOrder call() {
            return handleInstOrder(instOrderId);
        }
    }

}
