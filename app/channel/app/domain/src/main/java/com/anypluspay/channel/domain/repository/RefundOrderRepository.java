package com.anypluspay.channel.domain.repository;

import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;

import java.util.List;

/**
 * @author wxj
 * 2024/8/21
 */
public interface RefundOrderRepository {

    /**
     * 根据原始订单id查询所有退款订单
     * @param orderId
     * @return
     */
    List<RefundOrder> loadByOrigOrderId(String orderId);
}
