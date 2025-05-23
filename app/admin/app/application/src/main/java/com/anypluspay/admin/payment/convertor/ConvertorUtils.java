package com.anypluspay.admin.payment.convertor;

import com.anypluspay.commons.convertor.GlobalConvertorUtils;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.types.biz.TradeType;
import com.anypluspay.payment.types.biz.AcquiringOrderStatus;
import com.anypluspay.payment.types.biz.DepositOrderStatus;

/**
 * @author wxj
 * 2025/5/19
 */
public class ConvertorUtils extends GlobalConvertorUtils {

    public static String toTradeType(String tradeType) {
        TradeType statusEnumObject = EnumUtil.getByCode(TradeType.class, tradeType);
        return statusEnumObject != null ? statusEnumObject.getDisplayName() : "未知";
    }

    public static String toAcquiringStatus(String status) {
        AcquiringOrderStatus statusEnumObject = EnumUtil.getByCode(AcquiringOrderStatus.class, status);
        return statusEnumObject != null ? statusEnumObject.getDisplayName() : "未知";
    }

    public static String toDepositStatus(String status) {
        DepositOrderStatus statusEnumObject = EnumUtil.getByCode(DepositOrderStatus.class, status);
        return statusEnumObject != null ? statusEnumObject.getDisplayName() : "未知";
    }



}
