package com.anypluspay.account.domain.repository;

import com.anypluspay.account.domain.accounting.AccountingEntry;

import java.util.List;

/**
 * @author wxj
 * 2025/1/8
 */
public interface AccountingEntryRepository {

    void store(List<AccountingEntry> accountEntries);
}
