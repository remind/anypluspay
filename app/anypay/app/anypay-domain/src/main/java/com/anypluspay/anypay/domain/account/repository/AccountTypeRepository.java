package com.anypluspay.anypay.domain.account.repository;


import com.anypluspay.anypay.domain.account.OuterAccountType;

/**
 * @author wxj
 * 2023/12/22
 */
public interface AccountTypeRepository {

    OuterAccountType load(String code);
}
