package com.anypluspay.account.infra.repository;

import com.anypluspay.account.domain.AccountTransaction;
import com.anypluspay.account.domain.repository.AccountTransactionRepository;
import com.anypluspay.account.infra.persistence.convertor.AccountTransactionDalConvertor;
import com.anypluspay.account.infra.persistence.mapper.AccountTransactionMapper;
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
    public void store(AccountTransaction accountTransaction) {
        mapper.insert(dalConvertor.toDO(accountTransaction));
    }
}
