package com.anypluspay.account.infra.persistence.repository;

import com.anypluspay.account.domain.accounting.AccountingEntry;
import com.anypluspay.account.domain.repository.AccountingEntryRepository;
import com.anypluspay.account.infra.persistence.convertor.AccountingEntryDalConvertor;
import com.anypluspay.account.infra.persistence.mapper.AccountingEntryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2025/1/8
 */
@Repository
public class AccountingEntryRepositoryImpl implements AccountingEntryRepository {

    @Autowired
    private AccountingEntryDalConvertor dalConvertor;

    @Autowired
    private AccountingEntryMapper dalMapper;

    @Override
    public void store(List<AccountingEntry> accountEntries) {
        dalMapper.insert(dalConvertor.toDO(accountEntries));
    }
}
