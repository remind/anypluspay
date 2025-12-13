package com.anypluspay.anypay.account.repository;


import com.anypluspay.anypay.account.AccountTitle;
import com.anypluspay.anypay.types.account.AccountTitleType;

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
