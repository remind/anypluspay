package com.anypluspay.admin.account.convertor;

import com.anypluspay.account.infra.persistence.dataobject.InnerAccountDetailDO;
import com.anypluspay.admin.account.response.InnerAccountDetailResponse;
import com.anypluspay.component.dal.PageReadConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/5/8
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InnerAccountDetailConvertor extends PageReadConvertor<InnerAccountDetailResponse, InnerAccountDetailDO> {

    @Mapping(target = "beforeBalance", expression = "java(ConvertorUtils.toDisplayMoney(doObject.getBeforeBalance(), doObject.getCurrencyCode()))")
    @Mapping(target = "afterBalance", expression = "java(ConvertorUtils.toDisplayMoney(doObject.getAfterBalance(), doObject.getCurrencyCode()))")
    @Mapping(target = "amount", expression = "java(ConvertorUtils.toDisplayMoney(doObject.getAmount(), doObject.getCurrencyCode()))")
    @Mapping(target = "crDr", expression = "java(ConvertorUtils.toCrDrName(doObject.getCrDr()))")
    @Mapping(target = "ioDirection", expression = "java(ConvertorUtils.toIoDirectionName(doObject.getIoDirection()))")
    @Override
    InnerAccountDetailResponse toEntity(InnerAccountDetailDO doObject);
}
