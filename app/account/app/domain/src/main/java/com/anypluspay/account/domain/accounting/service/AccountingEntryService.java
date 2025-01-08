package com.anypluspay.account.domain.accounting.service;

import com.anypluspay.account.domain.accounting.AccountingEntry;
import com.anypluspay.account.domain.repository.AccountingEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会计分录操作
 * @author wxj
 * 2025/1/8
 */
@Service
public class AccountingEntryService {

    @Autowired
    private AccountingEntryRepository accountingEntryRepository;

    /**
     * 新增分录
     * @param accountingEntries 分录明细
     */
    public void addEntry(List<AccountingEntry> accountingEntries) {
        accountingEntryRepository.store(accountingEntries);
    }
}
