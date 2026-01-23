package com.anypluspay.anypay.infra.persistence.account.convertor;

import com.anypluspay.anypay.domain.account.OuterAccountType;
import com.anypluspay.anypay.infra.persistence.dataobject.OuterAccountTypeDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2023/12/22
 */
@Mapper(componentModel = "spring", uses = {AccountEnumsConvertor.class})
public interface OuterAccountTypeDalConvertor extends ReadWriteConvertor<OuterAccountType, OuterAccountTypeDO> {

    @Override
    OuterAccountType toEntity(OuterAccountTypeDO outerAccountTypeDO);

    @Override
    OuterAccountTypeDO toDO(OuterAccountType outerAccountType);

}
