package com.anypluspay.payment.infra.persistence;

import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.domain.flux.FluxOrderStatus;
import com.anypluspay.payment.domain.flux.InstructStatus;
import com.anypluspay.payment.domain.flux.InstructionType;
import com.anypluspay.payment.domain.payorder.PayOrderStatus;
import com.anypluspay.payment.domain.payorder.RefundOrderStatus;
import com.anypluspay.payment.types.PaymentType;
import com.anypluspay.payment.types.asset.AssetInfo;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundActionType;
import org.mapstruct.Mapper;

/**
 * @author wxj
 * 2024/1/18
 */
@Mapper(componentModel = "spring")
public interface EnumsConvertor {

    default PaymentType toPaymentType(String code) {
        return EnumUtil.getByCode(PaymentType.class, code);
    }

    default PayOrderStatus toPayOrderStatus(String code) {
        return EnumUtil.getByCode(PayOrderStatus.class, code);
    }

    default RefundOrderStatus toRefundOrderStatus(String code) {
        return EnumUtil.getByCode(RefundOrderStatus.class, code);
    }

    default FundActionType toFundActionType(String code) {
        return EnumUtil.getByCode(FundActionType.class, code);
    }

    default BelongTo toBelongTo(String code) {
        return EnumUtil.getByCode(BelongTo.class, code);
    }

    default FluxOrderStatus toFluxOrderStatus(String code) {
        return EnumUtil.getByCode(FluxOrderStatus.class, code);
    }

    default InstructionType toInstructionType(String code) {
        return EnumUtil.getByCode(InstructionType.class, code);
    }

    default InstructStatus toInstructStatus(String code) {
        return EnumUtil.getByCode(InstructStatus.class, code);
    }

    default FundAction toFundAction(String code) {
        return EnumUtil.getByCode(FundAction.class, code);
    }

    default AssetInfo toAssetInfo(String type, String jsonString) {
        return AssetInfo.parse(type, jsonString);
    }

    default String toAssetType(AssetInfo assetInfo) {
        return assetInfo.getAssetType().getCode();
    }
    default String toAssetJsonString(AssetInfo assetInfo) {
        return assetInfo.toJsonStr();
    }
}
