package com.anypluspay.payment.infra.persistence.repository.process;

import com.anypluspay.payment.domain.pay.BasePayOrder;
import com.anypluspay.payment.infra.persistence.convertor.FundDetailDalConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.FundDetailDO;
import com.anypluspay.payment.infra.persistence.mapper.FundDetailMapper;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundDetail;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wxj
 * 2025/2/23
 */
public abstract class AbstractPayOrderRepository {

    @Autowired
    private FundDetailMapper fundDetailMapper;

    @Autowired
    private FundDetailDalConvertor fundDetailDalConvertor;

    protected void fillFundDetails(BasePayOrder payOrder) {
        if (payOrder != null) {
            List<FundDetail> fundDetails = loadFundDetailByOrderId(payOrder.getOrderId());
            payOrder.setPayeeDetails(fundDetails.stream().filter(fundDetail -> fundDetail.getBelongTo() == BelongTo.PAYEE).toList());
            payOrder.setPayerDetails(fundDetails.stream().filter(fundDetail -> fundDetail.getBelongTo() == BelongTo.PAYER).toList());
        }
    }

    protected List<FundDetail> loadFundDetailByOrderId(String orderId) {
        return fundDetailDalConvertor.toEntity(fundDetailMapper.selectList(buildOrderIdQueryWrapper(orderId)));
    }

    protected void storeFundDetail(List<FundDetail> fundDetails) {
        fundDetails.forEach(fundDetail -> {
            fundDetailMapper.insert(fundDetailDalConvertor.toDO(fundDetail));
        });
    }

    protected void reStoreFundDetail(List<FundDetail> fundDetails) {
        fundDetails.forEach(fundDetail -> {
            fundDetailMapper.updateById(fundDetailDalConvertor.toDO(fundDetail));
        });
    }

    private Wrapper<FundDetailDO> buildOrderIdQueryWrapper(String orderId) {
        return new LambdaQueryWrapper<FundDetailDO>().eq(FundDetailDO::getOrderId, orderId);
    }
}
