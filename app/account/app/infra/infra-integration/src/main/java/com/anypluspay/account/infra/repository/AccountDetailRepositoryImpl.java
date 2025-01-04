package com.anypluspay.account.infra.repository;

import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.InnerAccountDetail;
import com.anypluspay.account.domain.detail.OuterAccountDetail;
import com.anypluspay.account.domain.repository.AccountDetailRepository;
import com.anypluspay.account.infra.persistence.convertor.InnerAccountDetailDalConvertor;
import com.anypluspay.account.infra.persistence.convertor.OuterAccountDetailDalConvertor;
import com.anypluspay.account.infra.persistence.dataobject.InnerAccountDetailDO;
import com.anypluspay.account.infra.persistence.dataobject.OuterAccountDetailDO;
import com.anypluspay.account.infra.persistence.dataobject.OuterSubAccountDetailDO;
import com.anypluspay.account.infra.persistence.mapper.InnerAccountDetailMapper;
import com.anypluspay.account.infra.persistence.mapper.OuterAccountDetailMapper;
import com.anypluspay.account.infra.persistence.mapper.OuterSubAccountDetailMapper;
import com.anypluspay.commons.exceptions.BizException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2023/12/21
 */
@Repository
public class AccountDetailRepositoryImpl implements AccountDetailRepository {

    @Autowired
    private OuterAccountDetailDalConvertor outDalConvertor;

    @Autowired
    private OuterAccountDetailMapper outDetailMapper;

    @Autowired
    private OuterSubAccountDetailMapper subDetailMapper;

    @Autowired
    private InnerAccountDetailDalConvertor innerDalConvertor;

    @Autowired
    private InnerAccountDetailMapper innerDetailMapper;

    @Override
    public List<AccountDetail> loadByRequestNo(String requestNo) {
        List<AccountDetail> accountDetails = new ArrayList<>();
        LambdaQueryWrapper<OuterAccountDetailDO> outerQueryWrapper = Wrappers.lambdaQuery();
        outerQueryWrapper.eq(OuterAccountDetailDO::getRequestNo, requestNo);
        List<OuterAccountDetailDO> outerAccountDetailDOS = outDetailMapper.selectList(outerQueryWrapper);
        if (!CollectionUtils.isEmpty(outerAccountDetailDOS)) {
            LambdaQueryWrapper<OuterSubAccountDetailDO> subOuterQueryWrapper = Wrappers.lambdaQuery();
            subOuterQueryWrapper.eq(OuterSubAccountDetailDO::getRequestNo, requestNo);
            List<OuterSubAccountDetailDO> outerSubAccountDetailDOS = subDetailMapper.selectList(subOuterQueryWrapper);
            outerAccountDetailDOS.forEach(outerAccountDetailDO -> {
                OuterAccountDetail outerAccountDetail = outDalConvertor.toEntity(outerAccountDetailDO
                        , outerSubAccountDetailDOS.stream()
                                .filter(outerSubAccountDetailDO -> outerSubAccountDetailDO.getVoucherNo().equals(outerAccountDetailDO.getVoucherNo()))
                                .toList()
                );
                accountDetails.add(outerAccountDetail);
            });
        }
        LambdaQueryWrapper<InnerAccountDetailDO> innerQueryWrapper = Wrappers.lambdaQuery();
        innerQueryWrapper.eq(InnerAccountDetailDO::getRequestNo, requestNo);
        accountDetails.addAll(innerDalConvertor.toEntity(innerDetailMapper.selectList(innerQueryWrapper)));
        return accountDetails;
    }

    @Override
    public void store(List<AccountDetail> accountDetails) {
        accountDetails.forEach(accountDetail -> {
            if (accountDetail instanceof OuterAccountDetail) {
                outerDetailStore((OuterAccountDetail) accountDetail);
            } else if (accountDetail instanceof InnerAccountDetail) {
                innerDetailStore((InnerAccountDetail) accountDetail);
            } else {
                throw new BizException("异常明细");
            }
        });
    }

    private void outerDetailStore(OuterAccountDetail detail) {
        outDetailMapper.insert(outDalConvertor.toDO(detail));
        detail.getOuterSubAccountDetails().forEach(outerSubAccountDetail -> {
            subDetailMapper.insert(outDalConvertor.subToDO(outerSubAccountDetail));
        });
    }

    private void innerDetailStore(InnerAccountDetail detail) {
        innerDetailMapper.insert(innerDalConvertor.toDO(detail));
    }

}
