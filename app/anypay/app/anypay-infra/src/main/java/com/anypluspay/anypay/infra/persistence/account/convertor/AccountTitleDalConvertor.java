package com.anypluspay.anypay.infra.persistence.account.convertor;

import com.anypluspay.anypay.account.AccountTitle;
import com.anypluspay.anypay.infra.persistence.dataobject.AccountTitleDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2023/12/10
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface AccountTitleDalConvertor extends ReadWriteConvertor<AccountTitle, AccountTitleDO> {

}
