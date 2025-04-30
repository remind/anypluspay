package com.anypluspay.admin.trade.convertor;

import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.testtrade.types.PayStatus;
import com.anypluspay.testtrade.types.RefundStatus;
import com.anypluspay.testtrade.types.TradeStatus;

/**
 * @author wxj
 * 2025/4/21
 */
public class ConvertorUtils {

    public static String toTradeStatus(String status) {
        TradeStatus tradeStatus = EnumUtil.getByCode(TradeStatus.class, status);
        return tradeStatus != null ? tradeStatus.getDisplayName() : "未知";
    }

    public static String toPayStatus(String status) {
        PayStatus payStatus = EnumUtil.getByCode(PayStatus.class, status);
        return payStatus != null ? payStatus.getDisplayName() : "未知";
    }

    public static String toRefundStatus(String status) {
        RefundStatus refundStatus = EnumUtil.getByCode(RefundStatus.class, status);
        return refundStatus != null ? refundStatus.getDisplayName() : "未知";
    }
}
