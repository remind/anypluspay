package com.anypluspay.channel.domain.repository;

import com.anypluspay.channel.domain.institution.InstOrder;

/**
 * @author wxj
 * 2024/7/13
 */
public interface InstOrderRepository {
    void store(InstOrder instOrder);

    void reStore(InstOrder instOrder);

    InstOrder load(Long instOrderId);

    InstOrder lock(Long instOrderId);

    InstOrder loadByInstRequestNo(String instRequestNo);

}
