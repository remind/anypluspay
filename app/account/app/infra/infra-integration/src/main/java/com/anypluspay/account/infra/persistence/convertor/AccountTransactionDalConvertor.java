package com.anypluspay.account.infra.persistence.convertor;

import com.anypluspay.account.domain.AccountTransaction;
import com.anypluspay.account.infra.persistence.dataobject.AccountTransactionDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2023/12/23
 */
@Mapper(componentModel = "spring")
public interface AccountTransactionDalConvertor extends ReadWriteConvertor<AccountTransaction, AccountTransactionDO> {
}
