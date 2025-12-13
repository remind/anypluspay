package com.anypluspay.anypay.infra.persistence.account.convertor;

import com.anypluspay.anypay.account.OuterAccountType;
import com.anypluspay.anypay.infra.persistence.dataobject.OuterAccountTypeDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2023/12/22
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface OuterAccountTypeDalConvertor extends ReadWriteConvertor<OuterAccountType, OuterAccountTypeDO> {

    @Mapping(target = "fundTypes", expression = "java(toList(outerAccountTypeDO.getFundTypes()))")
    @Override
    OuterAccountType toEntity(OuterAccountTypeDO outerAccountTypeDO);

    @Mapping(target = "fundTypes", expression = "java(toStr(outerAccountType.getFundTypes()))")
    @Override
    OuterAccountTypeDO toDO(OuterAccountType outerAccountType);

}
