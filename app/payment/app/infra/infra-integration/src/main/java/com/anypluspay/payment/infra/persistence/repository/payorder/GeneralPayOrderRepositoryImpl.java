package com.anypluspay.payment.infra.persistence.repository.payorder;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.payment.domain.payorder.BasePayOrder;
import com.anypluspay.payment.domain.payorder.general.GeneralPayOrder;
import com.anypluspay.payment.domain.repository.GeneralPayOrderRepository;
import com.anypluspay.payment.infra.persistence.convertor.GeneralPayOrderConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.OrderExtensionDO;
import com.anypluspay.payment.infra.persistence.mapper.GeneralPayOrderMapper;
import com.anypluspay.payment.infra.persistence.mapper.OrderExtensionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2024/1/27
 */
@Repository
public class GeneralPayOrderRepositoryImpl extends AbstractPayOrderRepository implements GeneralPayOrderRepository {

    @Autowired
    private GeneralPayOrderMapper dalMapper;

    @Autowired
    private GeneralPayOrderConvertor dalConvertor;

    @Autowired
    private OrderExtensionMapper extensionMapper;

    @Override
    public void store(GeneralPayOrder payOrder) {
        dalMapper.insert(dalConvertor.toDO(payOrder));
        if (StrUtil.isNotBlank(payOrder.getOrderExtension())) {
            extensionMapper.insert(buildOrderExtensionDO(payOrder));
        }
        storeFundDetail(payOrder.getPayerDetails());
        storeFundDetail(payOrder.getPayeeDetails());
    }

    @Override
    public void reStore(GeneralPayOrder generalPayOrder) {
        dalMapper.updateById(dalConvertor.toDO(generalPayOrder));
        extensionMapper.updateById(buildOrderExtensionDO(generalPayOrder));
    }

    @Override
    public GeneralPayOrder load(String payOrderId) {
        GeneralPayOrder generalPayOrder = dalConvertor.toEntity(dalMapper.selectById(payOrderId));
        fillFundDetails(generalPayOrder);
        return generalPayOrder;
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
