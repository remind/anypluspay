package com.anypluspay.channel.application.event;

import com.anypluspay.channel.domain.bizorder.OrderContext;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 渠道业务订单处理完成事件
 * @author wxj
 * 2025/4/9
 */
@Data
@AllArgsConstructor
public class BizOrderCompleteEvent {

    /**
     * 订单上下文
     */
    private OrderContext orderContext;
}
