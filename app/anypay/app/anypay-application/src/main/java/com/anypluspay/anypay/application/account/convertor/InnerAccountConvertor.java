package com.anypluspay.anypay.application.account.convertor;

import com.anypluspay.anypay.domain.account.InnerAccount;
import com.anypluspay.anypay.application.account.request.InnerAccountRequest;
import com.anypluspay.anypay.application.account.response.InnerAccountResponse;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2023/12/23
 */
@Mapper(componentModel = "spring")
public interface InnerAccountConvertor {

    InnerAccount toInnerAccount(InnerAccountRequest request);

    InnerAccountResponse toResponse(InnerAccount account);
}
