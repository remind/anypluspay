package com.anypluspay.channel.infra.persistence.bizorder.fundin;

import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.infra.persistence.dataobject.FundInOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/8/9
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface FundInConvertor extends BaseExpressionConvertor {

    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(fundInOrder.getAmount()))")
    @Mapping(target = "amount", expression = "java(toAmountValue(fundInOrder.getAmount()))")
    @Mapping(target = "terminal", expression = "java(terminalToString(fundInOrder.getTerminal()))")
    @Mapping(target = "terminalType", expression = "java(getTerminalType(fundInOrder.getTerminal()))")
    FundInOrderDO toDO(FundInOrder fundInOrder);

    @Mapping(target = "orderId", source = "bizOrderDO.orderId")
    @Mapping(target = "gmtCreate", source = "bizOrderDO.gmtCreate")
    @Mapping(target = "gmtModified", source = "bizOrderDO.gmtModified")
    @Mapping(target = "amount", expression = "java(toMoney(fundInOrderDO.getAmount(), fundInOrderDO.getCurrencyCode()))")
    @Mapping(target = "terminal", expression = "java(jsonStringToTerminal(fundInOrderDO.getTerminalType(),fundInOrderDO.getTerminal()))")
    FundInOrder toEntity(BizOrderDO bizOrderDO, FundInOrderDO fundInOrderDO);
}
