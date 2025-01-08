package com.anypluspay.account.infra.persistence.repository;

import com.anypluspay.account.domain.AccountTransaction;
import com.anypluspay.account.domain.repository.AccountTransactionRepository;
import com.anypluspay.account.infra.persistence.convertor.AccountTransactionDalConvertor;
import com.anypluspay.account.infra.persistence.dataobject.AccountTransactionDO;
import com.anypluspay.account.infra.persistence.mapper.AccountTransactionMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2023/12/21
 */
@Repository
public class AccountTransactionRepositoryImpl implements AccountTransactionRepository {

    @Autowired
    private AccountTransactionDalConvertor dalConvertor;

    @Autowired
    private AccountTransactionMapper mapper;

    @Override
    public AccountTransaction loadByRequestNo(String requestNo) {
        LambdaQueryWrapper<AccountTransactionDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AccountTransactionDO::getRequestNo, requestNo);
        return dalConvertor.toEntity(mapper.selectOne(queryWrapper));
    }

    @Override
    public void store(AccountTransaction accountTransaction) {
        mapper.insert(dalConvertor.toDO(accountTransaction));
    }
}
