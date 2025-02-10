package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.FluxInstructionDO;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.payment.types.asset.AssetInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2024/1/29
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface FluxInstructionDalConvertor extends ReadWriteConvertor<FluxInstruction, FluxInstructionDO> {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(instructionDO.getAmount(), instructionDO.getCurrencyCode()))")
    @Mapping(target = "assetInfo", expression = "java(enumsConvertor.toAssetInfo(instructionDO.getAssetType(), instructionDO.getAssetInfo()))")
    FluxInstruction toEntity(FluxInstructionDO instructionDO);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(instruction.getAmount()))")
    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(instruction.getAmount()))")
    @Mapping(target = "assetType", expression = "java(enumsConvertor.toAssetType(instruction.getAssetInfo()))")
    @Mapping(target = "assetInfo", expression = "java(enumsConvertor.toAssetJsonString(instruction.getAssetInfo()))")
    FluxInstructionDO toDO(FluxInstruction instruction);

}
