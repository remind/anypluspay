package com.anypluspay.account.domain.repository;

import com.anypluspay.account.domain.detail.AccountDetail;

import java.util.List;

/**
 * @author wxj
 * 2023/12/21
 */
public interface AccountDetailRepository {

    void store(List<AccountDetail> accountDetails);
}
