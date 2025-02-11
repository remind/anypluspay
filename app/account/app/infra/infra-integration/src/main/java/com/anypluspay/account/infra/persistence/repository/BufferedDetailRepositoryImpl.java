package com.anypluspay.account.infra.persistence.repository;

import com.anypluspay.account.domain.detail.BufferedDetail;
import com.anypluspay.account.domain.repository.BufferedDetailRepository;
import com.anypluspay.account.infra.persistence.convertor.BufferedDetailDalConvertor;
import com.anypluspay.account.infra.persistence.dataobject.BufferedDetailDO;
import com.anypluspay.account.infra.persistence.mapper.BufferedDetailMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2023/12/21
 */
@Repository
public class BufferedDetailRepositoryImpl implements BufferedDetailRepository {

    @Autowired
    private BufferedDetailDalConvertor dalConvertor;

    @Autowired
    private BufferedDetailMapper mapper;

    @Override
    public void store(List<BufferedDetail> bufferedDetails) {
        bufferedDetails.forEach(detail -> {
            mapper.insert(dalConvertor.toDO(detail));
        });
    }

    @Override
    public void reStore(BufferedDetail bufferedDetail) {
        mapper.updateById(dalConvertor.toDO(bufferedDetail));
    }

    @Override
    public BufferedDetail lock(String voucherNo) {
        return dalConvertor.toEntity(mapper.lockById(voucherNo));
    }

    @Override
    public BufferedDetail load(String voucherNo) {
        return dalConvertor.toEntity(mapper.selectById(voucherNo));
    }

    @Override
    public List<BufferedDetail> loadByRequestNo(String requestNo) {
        LambdaQueryWrapper<BufferedDetailDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(BufferedDetailDO::getRequestNo, requestNo);
        return dalConvertor.toEntity(mapper.selectList(queryWrapper));
    }

}
