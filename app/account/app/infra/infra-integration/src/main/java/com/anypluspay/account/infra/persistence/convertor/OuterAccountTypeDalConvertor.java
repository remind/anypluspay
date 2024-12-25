package com.anypluspay.account.infra.persistence.convertor;

import com.anypluspay.account.domain.OuterAccountType;
import com.anypluspay.account.infra.convertor.EnumsConvertor;
import com.anypluspay.account.infra.persistence.dataobject.OuterAccountTypeDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    default List<String> toList(String str) {
        return StringUtils.isBlank(str) ? new ArrayList<>() : Arrays.asList(StringUtils.split(str,","));
    }

    default String toStr(List<String> list) {
        return CollectionUtils.isEmpty(list) ? "" : StringUtils.join(list, ",");
    }
}
