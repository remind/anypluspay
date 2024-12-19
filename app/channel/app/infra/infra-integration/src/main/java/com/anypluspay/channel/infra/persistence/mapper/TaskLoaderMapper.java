package com.anypluspay.channel.infra.persistence.mapper;


import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务加载数据
 *
 * @author wxj
 * 2024/12/13
 */
public interface TaskLoaderMapper {

    /**
     * 加载查询任务的机构订单ID
     *
     * @param queryTask
     * @return
     */
    List<Long> loadInstOrderByQueryTask(@Param("query") TaskQuery queryTask);

    /**
     * 加载延迟机构订单
     * @param query
     * @return
     */
    List<Long> loadDelayInstOrder(@Param("query") TaskQuery query);

    /**
     * 获取机构订单的锁状态
     *
     * @param instOrderId
     * @param lockStatus
     * @param unLockStatus
     * @return
     */
    int tryLock(@Param("instOrderId") Long instOrderId, @Param("lockStatus") String lockStatus, @Param("unLockStatus") String unLockStatus);

    /**
     * 释放机构订单的任务锁
     *
     * @param instOrderId
     * @param lockStatus
     * @param unLockStatus
     * @return
     */
    int releaseLock(@Param("instOrderId") Long instOrderId, @Param("lockStatus") String lockStatus, @Param("unLockStatus") String unLockStatus, @Param("nextRetryTime") LocalDateTime nextRetryTime);

}
