package com.anypluspay.account.infra.repository;

import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.InnerAccountDetail;
import com.anypluspay.account.domain.detail.OuterAccountDetail;
import com.anypluspay.account.domain.repository.AccountDetailRepository;
import com.anypluspay.account.infra.persistence.convertor.InnerAccountDetailDalConvertor;
import com.anypluspay.account.infra.persistence.convertor.OuterAccountDetailDalConvertor;
import com.anypluspay.account.infra.persistence.convertor.OuterSubAccountDetailDalConvertor;
import com.anypluspay.account.infra.persistence.mapper.InnerAccountDetailMapper;
import com.anypluspay.account.infra.persistence.mapper.OuterAccountDetailMapper;
import com.anypluspay.account.infra.persistence.mapper.OuterSubAccountDetailMapper;
import com.anypluspay.commons.exceptions.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    private OuterSubAccountDetailDalConvertor subDalConvertor;

    @Autowired
    private OuterSubAccountDetailMapper subDetailMapper;

    @Autowired
    private InnerAccountDetailDalConvertor innerDalConvertor;

    @Autowired
    private InnerAccountDetailMapper innerDetailMapper;

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
            subDetailMapper.insert(subDalConvertor.toDO(outerSubAccountDetail));
        });
    }

    private void innerDetailStore(InnerAccountDetail detail) {
        innerDetailMapper.insert(innerDalConvertor.toDO(detail));
    }

}
