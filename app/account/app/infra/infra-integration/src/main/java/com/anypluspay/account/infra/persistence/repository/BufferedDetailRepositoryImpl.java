package com.anypluspay.account.infra.persistence.repository;

import com.anypluspay.account.domain.detail.BufferedDetail;
import com.anypluspay.account.domain.repository.BufferedDetailRepository;
import com.anypluspay.account.infra.persistence.convertor.BufferedDetailDalConvertor;
import com.anypluspay.account.infra.persistence.dataobject.BufferedDetailDO;
import com.anypluspay.account.infra.persistence.mapper.BufferedDetailMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        mapper.update(dalConvertor.toDO(bufferedDetail), getIdWrapper(bufferedDetail.getVoucherNo()));
    }

    @Override
    public BufferedDetail lock(String voucherNo) {
        return dalConvertor.toEntity(mapper.lockOne(getIdWrapper(voucherNo)));
    }

    private Wrapper<BufferedDetailDO> getIdWrapper(String voucherNo) {
        return new LambdaQueryWrapper<BufferedDetailDO>().eq(BufferedDetailDO::getVoucherNo, voucherNo);
    }
}
