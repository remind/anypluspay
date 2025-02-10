package com.anypluspay.payment.infra.persistence.repository.inner;

import com.anypluspay.payment.infra.persistence.convertor.FundDetailDalConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.FundDetailDO;
import com.anypluspay.payment.infra.persistence.mapper.FundDetailMapper;
import com.anypluspay.payment.types.funds.FundDetail;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2024/1/18
 */
@Repository
public class FundDetailInnerRepository  {

    @Autowired
    private FundDetailMapper dalMapper;

    @Autowired
    private FundDetailDalConvertor dalConvertor;

    public List<FundDetail> loadByPaymentId(String paymentId) {
        return dalConvertor.toEntity(dalMapper.selectList(buildPaymentIdQueryWrapper(paymentId)));
    }

    public void store(List<FundDetail> fundDetails) {
        fundDetails.forEach(fundDetail -> {
            dalMapper.insert(dalConvertor.toDO(fundDetail));
        });
    }

    public void reStore(List<FundDetail> fundDetails) {
        fundDetails.forEach(fundDetail -> {
            dalMapper.updateById(dalConvertor.toDO(fundDetail));
        });
    }

    private Wrapper<FundDetailDO> buildPaymentIdQueryWrapper(String paymentId) {
        return new LambdaQueryWrapper<FundDetailDO>().eq(FundDetailDO::getPaymentId, paymentId);
    }
}
