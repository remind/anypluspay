package com.anypluspay.payment.infra.persistence.repository.process;

import com.anypluspay.payment.domain.process.PayProcess;
import com.anypluspay.payment.domain.repository.PayProcessRepository;
import com.anypluspay.payment.infra.persistence.convertor.PayProcessConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.PayProcessDO;
import com.anypluspay.payment.infra.persistence.mapper.PayProcessMapper;
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
public class PayProcessRepositoryImpl extends AbstractPayOrderRepository implements PayProcessRepository {

    @Autowired
    private PayProcessMapper dalMapper;

    @Autowired
    private PayProcessConvertor dalConvertor;

    @Override
    public void store(PayProcess payProcess) {
        dalMapper.insert(dalConvertor.toDO(payProcess));
        storeFundDetail(payProcess.getPayerDetails());
        storeFundDetail(payProcess.getPayeeDetails());
    }

    @Override
    public void reStore(PayProcess payProcess) {
        dalMapper.updateById(dalConvertor.toDO(payProcess));
    }

    @Override
    public PayProcess load(String payProcessId) {
        PayProcess payProcess = dalConvertor.toEntity(dalMapper.selectById(payProcessId));
        fillFundDetails(payProcess);
        return payProcess;
    }

    @Override
    public PayProcess lock(String payProcessId) {
        PayProcess payProcess = dalConvertor.toEntity(dalMapper.lockById(payProcessId));
        fillFundDetails(payProcess);
        return payProcess;
    }

    @Override
    public List<PayProcess> loadByPaymentId(String paymentId) {
        LambdaQueryWrapper<PayProcessDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayProcessDO::getPaymentId, paymentId);
        List<PayProcess> payProcesses = dalConvertor.toEntity(dalMapper.selectList(queryWrapper));
        if (!CollectionUtils.isEmpty(payProcesses)) {
            payProcesses.forEach(this::fillFundDetails);
        }
        return payProcesses;
    }

}
