package com.anypluspay.payment.infra.persistence.repository.process;

import com.anypluspay.payment.domain.process.refund.RefundProcess;
import com.anypluspay.payment.domain.repository.RefundProcessRepository;
import com.anypluspay.payment.infra.persistence.convertor.RefundProcessDalConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.RefundProcessDO;
import com.anypluspay.payment.infra.persistence.mapper.RefundProcessMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 退款仓储
 *
 * @author wxj
 * 2025/2/22
 */
@Repository
public class RefundProcessRepositoryImpl extends AbstractPayOrderRepository implements RefundProcessRepository {

    @Autowired
    private RefundProcessMapper dalMapper;

    @Autowired
    private RefundProcessDalConvertor dalConvertor;

    @Override
    public void store(RefundProcess refundProcess) {
        dalMapper.insert(dalConvertor.toDO(refundProcess));
        storeFundDetail(refundProcess.getPayerDetails());
        storeFundDetail(refundProcess.getPayeeDetails());
    }

    @Override
    public void reStore(RefundProcess refundProcess) {
        dalMapper.updateById(dalConvertor.toDO(refundProcess));
    }

    @Override
    public RefundProcess load(String refundProcessId) {
        RefundProcessDO refundProcessDO = dalMapper.selectById(refundProcessId);
        RefundProcess refundProcess = dalConvertor.toEntity(refundProcessDO);
        fillFundDetails(refundProcess);
        return refundProcess;
    }

    @Override
    public RefundProcess lock(String refundProcessId) {
        RefundProcessDO refundProcessDO = dalMapper.lockById(refundProcessId);
        RefundProcess refundProcess = dalConvertor.toEntity(refundProcessDO);
        fillFundDetails(refundProcess);
        return refundProcess;
    }

    @Override
    public List<RefundProcess> loadByPayProcessId(String payProcessId) {
        LambdaQueryWrapper<RefundProcessDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RefundProcessDO::getRelationId, payProcessId);
        List<RefundProcess> refundProcesses = dalConvertor.toEntity(dalMapper.selectList(wrapper));
        if (!CollectionUtils.isEmpty(refundProcesses)) {
            refundProcesses.forEach(this::fillFundDetails);
        }
        return refundProcesses;
    }

}
