package com.anypluspay.anypay.infra.persistence.trade.convertor;

import com.anypluspay.anypay.types.trade.AcquiringOrderStatus;
import com.anypluspay.anypay.types.trade.DepositOrderStatus;
import com.anypluspay.anypay.types.trade.TradeType;
import com.anypluspay.anypay.types.trade.WithdrawOrderStatus;
import com.anypluspay.commons.lang.utils.EnumUtil;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2024/1/18
 */
@Mapper(componentModel = "spring")
public interface TradeEnumsConvertor {

    default DepositOrderStatus toDepositOrderStatus(String code) {
        return EnumUtil.getByCode(DepositOrderStatus.class, code);
    }

    default WithdrawOrderStatus toWithdrawOrderStatus(String code) {
        return EnumUtil.getByCode(WithdrawOrderStatus.class, code);
    }

    default TradeType toTradeType(String code) {
        return EnumUtil.getByCode(TradeType.class, code);
    }

    default AcquiringOrderStatus toTradeOrderStatus(String code) {
        return EnumUtil.getByCode(AcquiringOrderStatus.class, code);
    }

}
