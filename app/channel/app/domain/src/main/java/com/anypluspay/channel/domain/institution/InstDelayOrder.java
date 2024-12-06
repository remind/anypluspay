package com.anypluspay.channel.domain.institution;

import com.anypluspay.channel.types.order.DelayOrderStatus;
import com.anypluspay.commons.lang.Entity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 渠道延迟订单
 *
 * @author wxj
 * 2024/8/16
 */
@Data
public class InstDelayOrder extends Entity {

    /**
     * 机构订单单ID
     */
    private String instOrderId;

    /**
     * 状态
     */
    private DelayOrderStatus status;

    /**
     * 处理次数
     */
    private int count;

    /**
     * 预约处理时间
     */
    private LocalDateTime bookProcessTime;

    /**
     * 最后处理时间
     */
    private LocalDateTime lastProcessTime;

}
