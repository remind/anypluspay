package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.payment.infra.persistence.dataobject.FundDetailDO;
import com.anypluspay.payment.types.asset.AssetInfo;
import com.anypluspay.payment.types.funds.FundDetail;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.commons.convertor.ReadWriteConvertor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author wxj
 * 2024/1/17
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface FundDetailDalConvertor extends ReadWriteConvertor<FundDetail, FundDetailDO> {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(fundDetailDO.getAmount(), fundDetailDO.getCurrencyCode()))")
    @Mapping(target = "assetInfo", expression = "java(enumsConvertor.toAssetInfo(fundDetailDO.getAssetType(), fundDetailDO.getAssetInfo()))")
    FundDetail toEntity(FundDetailDO fundDetailDO);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(fundDetail.getAmount()))")
    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(fundDetail.getAmount()))")
    @Mapping(target = "assetType", expression = "java(enumsConvertor.toAssetType(fundDetail.getAssetInfo()))")
    @Mapping(target = "assetInfo", expression = "java(enumsConvertor.toAssetJsonString(fundDetail.getAssetInfo()))")
    FundDetailDO toDO(FundDetail fundDetail);

}
