package com.anypluspay.channel.domain.institution.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.InstProcessOrder;
import com.anypluspay.channel.domain.institution.service.InstOrderService;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.domain.repository.InstProcessOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wxj
 * 2024/8/23
 */
@Service
public class InstOrderServiceImpl implements InstOrderService {

    @Autowired
    private InstOrderRepository instOrderRepository;

    @Autowired
    private InstProcessOrderRepository instProcessOrderRepository;

    @Override
    public InstProcessOrder loadMainProcessOrder(InstOrder instOrder) {
        List<InstProcessOrder> instProcessOrders = instProcessOrderRepository.loadByInstOrderId(instOrder.getInstOrderId());
        if (CollectionUtil.isNotEmpty(instProcessOrders)) {
            return instProcessOrders.stream().filter(instProcessOrder -> instProcessOrder.getApiType() == instOrder.getApiType()).findFirst().orElse(null);
        }
        return null;
    }
}
