package com.anypluspay.payment.infra.persistence.convertor.query;

import com.anypluspay.component.dal.PageConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.DepositOrderDO;
import com.anypluspay.payment.types.trade.query.deposit.DepositOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/7/4
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {QueryConvertorUtils.class})
public interface DepositQueryDalConvertor extends PageConvertor<DepositOrderResponse, DepositOrderDO> {

    @Mapping(target = "status", expression = "java(QueryConvertorUtils.toDepositStatus(doObject.getStatus()))")
    @Mapping(target = "amount", expression = "java(QueryConvertorUtils.toDisplayMoney(doObject.getAmount(), doObject.getCurrencyCode()))")
    @Mapping(target = "gmtCreate", expression = "java(QueryConvertorUtils.dateToString(doObject.getGmtCreate()))")
    @Mapping(target = "gmtModified", expression = "java(QueryConvertorUtils.dateToString(doObject.getGmtModified()))")
    @Override
    DepositOrderResponse toEntity(DepositOrderDO doObject);
}