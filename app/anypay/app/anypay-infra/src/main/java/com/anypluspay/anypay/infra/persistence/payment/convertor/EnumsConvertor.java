package com.anypluspay.anypay.infra.persistence.payment.convertor;

import com.anypluspay.anypay.types.common.PayOrderStatus;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2026/1/29
 */
@Mapper(componentModel = "spring")
public interface EnumsConvertor {

    default PayOrderStatus toPayOrderStatus(com.anypluspay.anypay.types.trade.TradeOrderStatus tradeOrderStatus) {
        return PayOrderStatus.valueOf(tradeOrderStatus.name());
    }
}
