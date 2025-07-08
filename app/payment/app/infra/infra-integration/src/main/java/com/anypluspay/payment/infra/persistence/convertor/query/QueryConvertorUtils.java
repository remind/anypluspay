package com.anypluspay.payment.infra.persistence.convertor.query;

import com.anypluspay.commons.convertor.GlobalConvertorUtils;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.types.asset.AssetType;
import com.anypluspay.payment.types.paymethod.PayMethodStatus;
import com.anypluspay.payment.types.paymethod.PayModel;
import com.anypluspay.payment.types.trade.AcquiringOrderStatus;
import com.anypluspay.payment.types.trade.DepositOrderStatus;
import com.anypluspay.payment.types.trade.TradeType;

/**
 * @author wxj
 * 2025/7/4
 */
public class QueryConvertorUtils extends GlobalConvertorUtils {

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

    public static String toPayMethodStatusName(String status) {
        PayMethodStatus statusEnumObject = EnumUtil.getByCode(PayMethodStatus.class, status);
        return statusEnumObject != null ? statusEnumObject.getDisplayName() : "未知";
    }

    public static String toPayModelName(String payModel) {
        PayModel payModelEnumObject = EnumUtil.getByCode(PayModel.class, payModel);
        return payModelEnumObject != null ? payModelEnumObject.getDisplayName() : "未知";
    }

    public static String toAssetTypeName(String assetType) {
        AssetType assetTypeEnumObject = EnumUtil.getByCode(AssetType.class, assetType);
        return assetTypeEnumObject != null ? assetTypeEnumObject.getDisplayName() : "未知";
    }

}
