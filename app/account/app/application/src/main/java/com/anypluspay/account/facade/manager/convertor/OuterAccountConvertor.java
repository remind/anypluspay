package com.anypluspay.account.facade.manager.convertor;

import com.anypluspay.account.domain.OuterAccount;
import com.anypluspay.account.domain.OuterAccountType;
import com.anypluspay.account.facade.manager.dto.OuterAccountAddRequest;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2023/12/22
 */
@Mapper(componentModel = "spring")
public interface OuterAccountConvertor {
    OuterAccount toOuterAccount(OuterAccountAddRequest request, OuterAccountType outerAccountType);

    OuterAccountResponse toDto(OuterAccount account);
}
