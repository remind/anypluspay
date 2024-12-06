package com.anypluspay.channel.domain.repository;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;

/**
 * 业务单据仓储
 * @author wxj
 * 2024/7/12
 */
public interface BizOrderRepository {

    void store(BaseBizOrder bizOrder);

    void reStore(BaseBizOrder bizOrder);

    BaseBizOrder load(String orderId);

    BaseBizOrder loadByRequestId(String requestId);

    BaseBizOrder lock(String orderId);

}
