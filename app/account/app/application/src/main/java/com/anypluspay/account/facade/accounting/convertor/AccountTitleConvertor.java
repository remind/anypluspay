package com.anypluspay.account.facade.accounting.convertor;

import com.anypluspay.account.domain.accounting.AccountTitle;
import com.anypluspay.account.facade.accounting.dto.AccountTitleAddRequest;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2023/12/16
 */
@Mapper(componentModel = "spring")
public interface AccountTitleConvertor {

    AccountTitle toEntity(AccountTitleAddRequest request);
}
