package com.anypluspay.account.facade.manager.convertor;

import com.anypluspay.account.domain.InnerAccount;
import com.anypluspay.account.facade.manager.dto.InnerAccountAddRequest;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2023/12/23
 */
@Mapper(componentModel = "spring")
public interface InnerAccountConvertor {

    InnerAccount toOuterAccount(InnerAccountAddRequest request);
}
