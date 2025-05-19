package com.anypluspay.admin.payment.convertor;

import com.anypluspay.admin.payment.response.DepositOrderResponse;
import com.anypluspay.component.dal.PageConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.DepositOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/5/19
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface DepositOrderConvertor extends PageConvertor<DepositOrderResponse, DepositOrderDO> {

    @Mapping(target = "status", expression = "java(ConvertorUtils.toDepositStatus(doObject.getStatus()))")
    @Mapping(target = "amount", expression = "java(ConvertorUtils.toDisplayMoney(doObject.getAmount(), doObject.getCurrencyCode()))")
    @Override
    DepositOrderResponse toEntity(DepositOrderDO doObject);
}