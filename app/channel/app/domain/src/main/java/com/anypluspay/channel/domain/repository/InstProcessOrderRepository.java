package com.anypluspay.channel.domain.repository;

import com.anypluspay.channel.domain.institution.InstProcessOrder;

import java.util.List;

/**
 * @author wxj
 * 2024/7/13
 */
public interface InstProcessOrderRepository {

    void reStore(InstProcessOrder instProcessOrder);

    void store(InstProcessOrder instProcessOrder);

    InstProcessOrder load(String instProcessOrderId);

    InstProcessOrder lock(String instProcessOrderId);

    List<InstProcessOrder> loadByInstOrderId(String instOrderId);
}
