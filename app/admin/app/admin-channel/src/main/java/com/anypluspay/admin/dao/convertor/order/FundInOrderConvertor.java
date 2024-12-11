package com.anypluspay.admin.dao.convertor.order;

import com.anypluspay.admin.dao.convertor.ConvertorUtils;
import com.anypluspay.admin.model.order.FundInOrderDto;
import com.anypluspay.channel.infra.persistence.dataobject.FundInOrderDO;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import com.anypluspay.commons.lang.utils.EnumUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/11/21
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FundInOrderConvertor extends BaseExpressionConvertor {

    @Mapping(target = "amount", expression = "java(toMoney(fundInOrderDO.getAmount(), fundInOrderDO.getCurrencyCode()))")
    FundInOrderDto convert(FundInOrderDO fundInOrderDO);

    default FundInOrderDto fill(FundInOrderDto fundInOrderDto) {
        if (fundInOrderDto != null) {
            fundInOrderDto.setStatusName(ConvertorUtils.toBizOrderStatusName(fundInOrderDto.getStatus()));
        }
        return fundInOrderDto;
    }

}
