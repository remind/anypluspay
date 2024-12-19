package com.anypluspay.channel.infra.persistence;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.infra.persistence.bizorder.BizOrderConvertor;
import com.anypluspay.channel.infra.persistence.bizorder.BizOrderDalOperatorContainer;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.channel.infra.persistence.mapper.BizOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author wxj
 * 2024/7/12
 */
@Repository
public class BizOrderRepositoryImpl implements BizOrderRepository {

    @Autowired
    private BizOrderConvertor dalConvertor;

    @Autowired
    private BizOrderMapper bizOrderMapper;

    @Autowired
    private BizOrderDalOperatorContainer dalOperatorContainer;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public void store(BaseBizOrder bizOrder) {
        transactionTemplate.executeWithoutResult(status -> {
            storeBaseOrder(bizOrder);
            dalOperatorContainer.getOperator(bizOrder.getRequestType().getCode()).store(bizOrder);
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void reStore(BaseBizOrder bizOrder) {
        transactionTemplate.executeWithoutResult(status -> {
            reStoreBaseOrder(bizOrder);
            dalOperatorContainer.getOperator(bizOrder.getRequestType().getCode()).reStore(bizOrder);
        });
    }

    @Override
    public BaseBizOrder load(String orderId) {
        return completeFill(bizOrderMapper.selectById(orderId));
    }

    @Override
    public BaseBizOrder loadByRequestId(String requestId) {
        LambdaQueryWrapper<BizOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BizOrderDO::getRequestId, requestId);
        return completeFill(bizOrderMapper.selectOne(queryWrapper));
    }

    @Override
    public BaseBizOrder lock(String orderId) {
        BizOrderDO bizOrderDO = bizOrderMapper.lockById(orderId);
        return completeFill(bizOrderDO);
    }

    private BaseBizOrder completeFill(BizOrderDO bizOrderDO) {
        if (bizOrderDO != null) {
            return dalOperatorContainer.getOperator(bizOrderDO.getRequestType()).load(bizOrderDO);
        }
        return null;
    }

    private void storeBaseOrder(BaseBizOrder baseBizOrder) {
        BizOrderDO bizOrderDO = dalConvertor.toDO(baseBizOrder);
        bizOrderMapper.insert(bizOrderDO);
    }

    private void reStoreBaseOrder(BaseBizOrder baseBizOrder) {
        BizOrderDO bizOrderDO = dalConvertor.toDO(baseBizOrder);
        bizOrderMapper.updateById(bizOrderDO);
    }
}
