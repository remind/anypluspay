package com.anypluspay.admin.trade.convertor;

import com.anypluspay.admin.trade.response.PayOrderResponse;
import com.anypluspay.component.dal.PageConvertor;
import com.anypluspay.testtrade.infra.persistence.dataobject.PayOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2025/4/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {ConvertorUtils.class})
public interface PayOrderConvertor extends PageConvertor<PayOrderResponse, PayOrderDO> {

    @Mapping(target = "status", expression = "java(ConvertorUtils.toPayStatus(doObject.getStatus()))")
    @Override
    PayOrderResponse toEntity(PayOrderDO doObject);

}
