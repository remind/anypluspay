package com.anypluspay.channel.infra.persistence.bizorder.refund;

import com.anypluspay.channel.domain.convertor.EnumsConvertor;
import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
import com.anypluspay.channel.infra.persistence.dataobject.RefundOrderDO;
import com.anypluspay.commons.convertor.BaseExpressionConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * @author wxj
 * 2024/8/20
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EnumsConvertor.class})
public interface RefundConvertor extends BaseExpressionConvertor {

    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(refundOrder.getAmount()))")
    @Mapping(target = "amount", expression = "java(toAmountValue(refundOrder.getAmount()))")
    RefundOrderDO toDO(RefundOrder refundOrder);

    @Mapping(target = "orderId", source = "bizOrderDO.orderId")
    @Mapping(target = "gmtCreate", source = "bizOrderDO.gmtCreate")
    @Mapping(target = "gmtModified", source = "bizOrderDO.gmtModified")
    @Mapping(target = "amount", expression = "java(toMoney(refundOrderDO.getAmount(), refundOrderDO.getCurrencyCode()))")
    RefundOrder toEntity(BizOrderDO bizOrderDO, RefundOrderDO refundOrderDO);

}
