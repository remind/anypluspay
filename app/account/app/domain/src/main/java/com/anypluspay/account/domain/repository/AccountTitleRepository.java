package com.anypluspay.account.domain.repository;

import com.anypluspay.account.domain.accounting.AccountTitle;
import com.anypluspay.account.types.accounting.AccountTitleType;

/**
 * @author wxj
 * 2023/12/16
 */
public interface AccountTitleRepository {

    void store(AccountTitle accountTitle);

    void reStore(AccountTitle accountTitle);

    AccountTitle load(String titleCode);

    AccountTitle lock(String titleCode);

    void delete(String titleCode);

    String getTitleCode(AccountTitleType type, Integer tier, String parentCode);
}
