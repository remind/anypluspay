package com.anypluspay.payment.infra.persistence.repository.payorder;

import com.anypluspay.payment.domain.payorder.general.GeneralPayOrder;
import com.anypluspay.payment.domain.repository.GeneralPayOrderRepository;
import com.anypluspay.payment.infra.persistence.convertor.GeneralPayOrderConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.GeneralPayOrderDO;
import com.anypluspay.payment.infra.persistence.mapper.GeneralPayOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

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

    @Override
    public void store(GeneralPayOrder payOrder) {
        dalMapper.insert(dalConvertor.toDO(payOrder));
        storeFundDetail(payOrder.getPayerDetails());
        storeFundDetail(payOrder.getPayeeDetails());
    }

    @Override
    public void reStore(GeneralPayOrder generalPayOrder) {
        dalMapper.updateById(dalConvertor.toDO(generalPayOrder));
    }

    @Override
    public GeneralPayOrder load(String payOrderId) {
        GeneralPayOrder generalPayOrder = dalConvertor.toEntity(dalMapper.selectById(payOrderId));
        fillFundDetails(generalPayOrder);
        return generalPayOrder;
    }

    @Override
    public List<GeneralPayOrder> loadByPaymentId(String paymentId) {
        LambdaQueryWrapper<GeneralPayOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GeneralPayOrderDO::getPaymentId, paymentId);
        List<GeneralPayOrder> generalPayOrders = dalConvertor.toEntity(dalMapper.selectList(queryWrapper));
        if (!CollectionUtils.isEmpty(generalPayOrders)) {
            generalPayOrders.forEach(this::fillFundDetails);
        }
        return generalPayOrders;
    }

}
