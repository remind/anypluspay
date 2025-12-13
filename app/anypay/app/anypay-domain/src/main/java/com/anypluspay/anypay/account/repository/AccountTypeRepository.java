package com.anypluspay.anypay.account.repository;


import com.anypluspay.anypay.account.OuterAccountType;

/**
 * @author wxj
 * 2023/12/22
 */
public interface AccountTypeRepository {

    OuterAccountType load(String code);
}
