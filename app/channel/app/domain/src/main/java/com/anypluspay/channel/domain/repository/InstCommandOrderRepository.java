package com.anypluspay.channel.domain.repository;

import com.anypluspay.channel.domain.institution.InstCommandOrder;

import java.util.List;

/**
 * @author wxj
 * 2024/7/13
 */
public interface InstCommandOrderRepository {

    void reStore(InstCommandOrder instCommandOrder);

    void store(InstCommandOrder instCommandOrder);

    InstCommandOrder load(Long commandId);

    InstCommandOrder lock(Long commandId);

    List<InstCommandOrder> loadByInstOrderId(Long instOrderId);
}
