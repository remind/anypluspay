package com.anypluspay.anypay.infra.persistence.trade.convertor;

import com.anypluspay.anypay.types.trade.*;
import com.anypluspay.commons.lang.utils.EnumUtil;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2024/1/18
 */
@Mapper(componentModel = "spring")
public interface TradeEnumsConvertor {

    default TradeType toTradeType(String code) {
        return EnumUtil.getByCode(TradeType.class, code);
    }

    default TradeNotifyStatus toTradeNotifyStatus(String code) {
        return EnumUtil.getByCode(TradeNotifyStatus.class, code);
    }

    default TradeOrderStatus toTradeOrderStatus(String code) {
        return EnumUtil.getByCode(TradeOrderStatus.class, code);
    }

}
