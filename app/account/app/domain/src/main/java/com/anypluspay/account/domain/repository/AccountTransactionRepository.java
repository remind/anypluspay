package com.anypluspay.account.domain.repository;

import com.anypluspay.account.domain.AccountTransaction;

/**
 * @author wxj
 * 2023/12/21
 */
public interface AccountTransactionRepository {

    void store(AccountTransaction accountTransaction);
}
