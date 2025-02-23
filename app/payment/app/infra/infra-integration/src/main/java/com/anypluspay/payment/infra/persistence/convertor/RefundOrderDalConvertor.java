package com.anypluspay.payment.infra.persistence.convertor;

import com.anypluspay.commons.convertor.ReadWriteConvertor;
import com.anypluspay.payment.domain.payorder.refund.RefundOrder;
import com.anypluspay.payment.infra.persistence.EnumsConvertor;
import com.anypluspay.payment.infra.persistence.dataobject.RefundOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author wxj
 * 2025/2/22
 */
@Mapper(componentModel = "spring", uses = {EnumsConvertor.class})
public interface RefundOrderDalConvertor extends ReadWriteConvertor<RefundOrder, RefundOrderDO> {

    @Override
    @Mapping(target = "amount", expression = "java(toMoney(refundOrderDO.getAmount(), refundOrderDO.getCurrencyCode()))")
    RefundOrder toEntity(RefundOrderDO refundOrderDO);

    @Override
    @Mapping(target = "amount", expression = "java(toAmountValue(refundOrder.getAmount()))")
    @Mapping(target = "currencyCode", expression = "java(toCurrencyCode(refundOrder.getAmount()))")
    RefundOrderDO toDO(RefundOrder refundOrder);
}
