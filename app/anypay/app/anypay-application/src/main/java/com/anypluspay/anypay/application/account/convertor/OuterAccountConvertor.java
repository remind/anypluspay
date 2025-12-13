package com.anypluspay.anypay.application.account.convertor;

import com.anypluspay.anypay.account.OuterAccount;
import com.anypluspay.anypay.account.OuterAccountType;
import com.anypluspay.anypay.application.account.request.OuterAccountRequest;
import com.anypluspay.anypay.application.account.response.OuterAccountResponse;
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
