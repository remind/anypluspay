package com.anypluspay.payment.infra.persistence.repository;

import com.anypluspay.payment.domain.repository.FundDetailRepository;
import com.anypluspay.payment.infra.persistence.convertor.FundDetailDalConvertor;
import com.anypluspay.payment.infra.persistence.mapper.FundDetailMapper;
import com.anypluspay.payment.types.funds.FundDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2025/4/3
 */
@Repository
public class FundDetailRepositoryImpl implements FundDetailRepository {

    @Autowired
    private FundDetailMapper dalMapper;

    @Autowired
    private FundDetailDalConvertor dalConvertor;

    @Override
    public FundDetail load(String detailId) {
        return dalConvertor.toEntity(dalMapper.selectById(detailId));
    }
}
