package com.anypluspay.account.infra.persistence.convertor;


import com.anypluspay.account.domain.accounting.AccountTitle;
import com.anypluspay.account.infra.convertor.EnumsConvertor;
import com.anypluspay.account.infra.persistence.dataobject.AccountTitleDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2023/12/10
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface AccountTitleDalConvertor extends ReadWriteConvertor<AccountTitle, AccountTitleDO> {

}
