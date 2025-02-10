package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.payorder.BasePayOrder;
import com.anypluspay.payment.domain.repository.BasePayOrderRepository;
import com.anypluspay.payment.infra.persistence.convertor.PayOrderDalConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.OrderExtensionDO;
import com.anypluspay.payment.infra.persistence.mapper.OrderExtensionMapper;
import com.anypluspay.payment.infra.persistence.mapper.PayOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2024/1/27
 */
@Repository
public class BasePayOrderRepositoryImpl implements BasePayOrderRepository {

    @Autowired
    private PayOrderMapper dalMapper;

    @Autowired
    private PayOrderDalConvertor dalConvertor;

    @Autowired
    private OrderExtensionMapper extensionMapper;

    @Override
    @SuppressWarnings("rawtypes")
    public void reStore(BasePayOrder payOrder) {
        dalMapper.updateById(dalConvertor.toDo(payOrder));
        extensionMapper.updateById(buildOrderExtensionDO(payOrder));
    }

    @SuppressWarnings("rawtypes")
    private OrderExtensionDO buildOrderExtensionDO(BasePayOrder payOrder) {
        OrderExtensionDO orderExtensionDO = new OrderExtensionDO();
        orderExtensionDO.setPaymentId(payOrder.getPaymentId());
        orderExtensionDO.setOrderId(payOrder.getOrderId());
        orderExtensionDO.setContent(payOrder.getOrderExtension());
        return orderExtensionDO;
    }
}
