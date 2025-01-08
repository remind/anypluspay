package com.anypluspay.account.facade.manager.convertor;

import com.anypluspay.account.domain.InnerAccount;
import com.anypluspay.account.facade.manager.request.InnerAccountRequest;
import com.anypluspay.account.facade.manager.response.InnerAccountResponse;
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
