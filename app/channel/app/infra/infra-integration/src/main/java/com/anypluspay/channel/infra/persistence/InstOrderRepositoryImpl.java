package com.anypluspay.channel.infra.persistence;

import com.anypluspay.channel.domain.institution.InstDelayOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.InstOrderType;
import com.anypluspay.channel.domain.repository.InstOrderRepository;
import com.anypluspay.channel.infra.persistence.convertor.InstDelayOrderDalConvertor;
import com.anypluspay.channel.infra.persistence.convertor.InstOrderDalConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.InstOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.InstDelayOrderMapper;
import com.anypluspay.channel.infra.persistence.mapper.InstOrderMapper;
import com.anypluspay.channel.types.order.ProcessTimeType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author wxj
 * 2024/8/6
 */
@Repository
public class InstOrderRepositoryImpl implements InstOrderRepository {

    @Autowired
    private InstOrderDalConvertor instOrderDalConvertor;

    @Autowired
    private InstDelayOrderDalConvertor instDelayOrderDalConvertor;

    @Autowired
    private InstOrderMapper instOrderMapper;

    @Autowired
    private InstDelayOrderMapper instDelayOrderMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public void store(InstOrder instOrder) {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            InstOrderDO instOrderDO = instOrderDalConvertor.toDO(instOrder, InstOrderType.FUND_IN.getCode());
            instOrderMapper.insert(instOrderDO);
            if (instOrder.getInstDelayOrder() != null) {
                instDelayOrderMapper.insert(instDelayOrderDalConvertor.toDO(instOrder.getInstDelayOrder()));
            }
        });
    }

    @Override
    public void reStore(InstOrder instOrder) {
        InstOrderDO instOrderDO = instOrderDalConvertor.toDO(instOrder, InstOrderType.FUND_IN.getCode());
        instOrderMapper.updateById(instOrderDO);
    }

    @Override
    public InstOrder load(String instOrderId) {
        InstOrderDO instOrderDO = instOrderMapper.selectById(instOrderId);
        return convert(instOrderDO);
    }

    @Override
    public InstOrder lock(String instOrderId) {
        return null;
    }

    @Override
    public InstOrder loadByInstRequestNo(String instRequestNo) {
        LambdaQueryWrapper<InstOrderDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InstOrderDO::getInstRequestNo, instRequestNo);
        InstOrderDO instOrderDO = instOrderMapper.selectOne(wrapper);
        return convert(instOrderDO);
    }

    @Override
    public void updateDelayOrder(InstDelayOrder instDelayOrder) {
        instDelayOrderMapper.updateById(instDelayOrderDalConvertor.toDO(instDelayOrder));
    }

    private InstOrder convert(InstOrderDO instOrderDO) {
        if (instOrderDO != null) {
            InstOrder instOrder = instOrderDalConvertor.toEntity(instOrderDO);
            if (instOrderDO.getProcessTimeType().equals(ProcessTimeType.DELAYED.getCode())) {
                instOrder.setInstDelayOrder(instDelayOrderDalConvertor.toEntity(instDelayOrderMapper.selectById(instOrderDO.getInstOrderId())));
            }
            return instOrderDalConvertor.toEntity(instOrderDO);
        } else {
            return null;
        }
    }
}
