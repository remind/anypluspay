package com.anypluspay.account.domain.repository;

import com.anypluspay.account.domain.OuterAccountType;

/**
 * @author wxj
 * 2023/12/22
 */
public interface AccountTypeRepository {

    OuterAccountType load(String code);
}
