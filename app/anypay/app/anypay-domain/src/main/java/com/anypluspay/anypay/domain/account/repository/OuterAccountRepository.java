package com.anypluspay.anypay.domain.account.repository;


import com.anypluspay.anypay.domain.account.OuterAccount;

import java.util.List;

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

    OuterAccount queryByMemberIdAndAccountType(String memberId, String accountType);
    List<OuterAccount> queryByMemberId(String memberId);

}
