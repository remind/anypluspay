package com.anypluspay.component.sequence;



import com.anypluspay.component.sequence.domain.Sequence;
import com.anypluspay.component.sequence.exception.SequenceUpdateException;
import com.anypluspay.component.sequence.repository.SequenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 序列内部服务
 * @author wxj
 * 2023/12/10
 */
public class SequenceInnerService {

    private final Logger logger = LoggerFactory.getLogger(SequenceInnerService.class);

    /**
     * 本地缓存的序列值
     */
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Long>> sequenceQueue = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();
    /**
     * 已使用数量
     */
    private final ConcurrentHashMap<String, AtomicInteger> counts = new ConcurrentHashMap<>();
    /**
     * 刷新阈值
     */
    private final Map<String, Integer> thresholds = new HashMap<>();
    /**
     * 单次取值个数
     */
    private final Map<String, Integer> totals = new HashMap<>();
    @Autowired
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private final SequenceRepository sequenceRepository;
    @Autowired
    private final TransactionTemplate transactionTemplate;

    /**
     * 获取序列值，如果异常会一直重试直到获取到
     * @param sequenceName
     * @return
     */
    public Long next(String sequenceName) {
        Long sequence = sequenceQueue.get(sequenceName).poll();
        if (sequence != null) {
            counts.get(sequenceName).incrementAndGet();
            if (overThreshold(sequenceName)) {
                asyncFlush(sequenceName);
            }
            return sequence;
        } else {
            try {
                flush(sequenceName);
                return next(sequenceName);
            } catch (Exception e) {
                logger.error("序列获取下一个值失败,进行重试，sequenceName={}", sequenceName);
                return next(sequenceName);
            }
        }
    }

    /**
     * 刷新缓存值
     * @param sequenceName
     */
    private void flush(String sequenceName) {
        if (overThreshold(sequenceName) && locks.get(sequenceName).tryLock()) {
            try {
                if (overThreshold(sequenceName)) {
                    try {
                        flushBuffer(sequenceName);
                    } catch (SequenceUpdateException e) {
                        logger.error("序列刷新失败,再异步重试，sequenceName={}", sequenceName);
                        asyncFlush(sequenceName);
                    }
                }
            } finally {
                locks.get(sequenceName).unlock();
            }
        }
    }

    /**
     * 刷新缓存值
     * @param sequenceName
     */
    private void flushBuffer(final String sequenceName) {
        transactionTemplate.executeWithoutResult(status -> {
            Sequence sequence = sequenceRepository.lock(sequenceName);
            int offset = sequence.getIncrement() * totals.get(sequenceName);
            Long beginValue = sequence.getCurrentValue();
            long endValue = beginValue + offset;

            try {
                sequenceRepository.update(sequenceName, beginValue, endValue);
            } catch (Exception e) {
                logger.error("序列刷新失败，sequenceName={}", sequenceName);
                throw new SequenceUpdateException(e);
            }
            sequenceQueue.computeIfAbsent(sequenceName, k -> new ConcurrentLinkedQueue<>());
            for(long seq = beginValue; seq < endValue; seq += sequence.getIncrement().longValue()) {
                sequenceQueue.get(sequenceName).add(seq);
            }
            counts.put(sequenceName, new AtomicInteger(0));
        });
    }

    /**
     * 判断当前已使用数量是否达到刷新值
     * @param sequenceName
     * @return
     */
    private boolean overThreshold(String sequenceName) {
        return counts.get(sequenceName).intValue() >= totals.get(sequenceName) - thresholds.get(sequenceName);
    }

    /**
     * 异步刷新
     * @param sequenceName
     */
    private void asyncFlush(final String sequenceName) {
        try {
            threadPoolTaskExecutor.execute(() -> flush(sequenceName));
        } catch (Exception e) {
            logger.error("序列异步刷新失败，sequenceName={}", sequenceName);
        }
    }

    /**
     * 加载所有序列
     */
    private void loadAll() {
        if (sequenceQueue.isEmpty()) {
            List<Sequence> sequenceList = sequenceRepository.loadAll();
            for (Sequence sequence : sequenceList) {
                thresholds.put(sequence.getName(), sequence.getThreshold());
                totals.put(sequence.getName(), sequence.getTotal());
                locks.put(sequence.getName(), new ReentrantLock());
                flushBuffer(sequence.getName());
            }
            logger.info("加载序列个数：{}", sequenceList.size());
        }
    }

    public SequenceInnerService(ThreadPoolTaskExecutor threadPoolTaskExecutor, SequenceRepository sequenceRepository, TransactionTemplate transactionTemplate) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.sequenceRepository = sequenceRepository;
        this.transactionTemplate = transactionTemplate;
        loadAll();
    }
}
