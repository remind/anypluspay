package com.anypluspay.account.facade.manager.convertor;

import com.anypluspay.account.domain.OuterAccount;
import com.anypluspay.account.domain.OuterAccountType;
import com.anypluspay.account.facade.manager.request.OuterAccountRequest;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2023/12/22
 */
@Mapper(componentModel = "spring")
public interface OuterAccountConvertor {
    OuterAccount toOuterAccount(OuterAccountRequest request, OuterAccountType outerAccountType);

    OuterAccountResponse toResponse(OuterAccount account);
}
