package com.anypluspay.channel.infra.persistence.mapper;

import lombok.Data;

/**
 * @author wxj
 * 2024/12/13
 */
@Data
public class TaskQuery {

    /**
     * 查询条数
     */
    private int size;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 订单状态
     */
    private String status;
}
