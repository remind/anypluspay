package com.anypluspay.channel.infra.persistence.bizorder.fundout;

import com.anypluspay.channel.domain.bizorder.fund.FundOutOrder;
import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.FundOutOrderDO;
import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/12/18
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface FundOutConvertor extends BaseExpressionConvertor {

    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(fundOutOrder.getAmount()))")
    @Mapping(target = "amount", expression = "java(toAmountValue(fundOutOrder.getAmount()))")
    FundOutOrderDO toDO(FundOutOrder fundOutOrder);

    @Mapping(target = "orderId", source = "bizOrderDO.orderId")
    @Mapping(target = "gmtCreate", source = "bizOrderDO.gmtCreate")
    @Mapping(target = "gmtModified", source = "bizOrderDO.gmtModified")
    @Mapping(target = "amount", expression = "java(toMoney(fundOutOrderDO.getAmount(), fundOutOrderDO.getCurrencyCode()))")
    FundOutOrder toEntity(BizOrderDO bizOrderDO, FundOutOrderDO fundOutOrderDO);
}
