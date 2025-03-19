package com.anypluspay.account.domain.repository;

import com.anypluspay.account.domain.OuterAccount;

/**
 * 账户仓储
 * @author wxj
 * 2023/12/16
 */
public interface OuterAccountRepository {

    String store(OuterAccount account);

    void reStore(OuterAccount account);

    OuterAccount load(String accountNo);

    OuterAccount lock(String accountNo);

    OuterAccount queryByMemberAndAccountTypeId(String memberId, String accountType);

}
