package com.anypluspay.channel.domain.repository;

import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.InstDelayOrder;

/**
 * @author wxj
 * 2024/7/13
 */
public interface InstOrderRepository {
    void store(InstOrder instOrder);

    void reStore(InstOrder instOrder);

    InstOrder load(String instOrderId);

    InstOrder lock(String instOrderId);

    InstOrder loadByInstRequestNo(String instRequestNo);

    void updateDelayOrder(InstDelayOrder instDelayOrder);
}
