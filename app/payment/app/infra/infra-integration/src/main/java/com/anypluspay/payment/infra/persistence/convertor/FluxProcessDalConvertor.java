package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.payment.domain.flux.FluxProcess;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.FluxProcessDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2024/1/29
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface FluxProcessDalConvertor extends ReadWriteConvertor<FluxProcess, FluxProcessDO> {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(doObject.getAmount(), doObject.getCurrencyCode()))")
    @Mapping(target = "assetInfo", expression = "java(enumsConvertor.toAssetInfo(doObject.getAssetType(), doObject.getAssetInfo()))")
    FluxProcess toEntity(FluxProcessDO doObject);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(entityObject.getAmount()))")
    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(entityObject.getAmount()))")
    @Mapping(target = "assetType", expression = "java(enumsConvertor.toAssetType(entityObject.getAssetInfo()))")
    @Mapping(target = "assetInfo", expression = "java(enumsConvertor.toAssetJsonString(entityObject.getAssetInfo()))")
    FluxProcessDO toDO(FluxProcess entityObject);

}
