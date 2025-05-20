package com.anypluspay.payment.infra.persistence;

import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.types.biz.DepositOrderStatus;
import com.anypluspay.payment.domain.flux.FluxOrderStatus;
import com.anypluspay.payment.domain.flux.FluxProcessStatus;
import com.anypluspay.payment.domain.flux.FluxProcessDirection;
import com.anypluspay.payment.domain.flux.FluxProcessType;
import com.anypluspay.payment.types.biz.AcquiringOrderStatus;
import com.anypluspay.payment.types.biz.WithdrawOrderStatus;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.TradeType;
import com.anypluspay.payment.types.paymethod.PayModel;
import com.anypluspay.payment.types.status.PayProcessStatus;
import com.anypluspay.payment.domain.process.refund.RefundOrderStatus;
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

    default PayProcessStatus toPayOrderStatus(String code) {
        return EnumUtil.getByCode(PayProcessStatus.class, code);
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

    default FluxProcessType toInstructionType(String code) {
        return EnumUtil.getByCode(FluxProcessType.class, code);
    }

    default FluxProcessStatus toInstructStatus(String code) {
        return EnumUtil.getByCode(FluxProcessStatus.class, code);
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

    default String toPayModel(PayModel payModel) {
        return payModel == null ? "" : payModel.getCode();
    }

    default PayModel toPayModel(String code) {
        return EnumUtil.getByCode(PayModel.class, code);
    }

    default PayOrderType toPayOrderType(String code) {
        return EnumUtil.getByCode(PayOrderType.class, code);
    }

    default FluxProcessDirection toInstructionDirection(String code) {
        return EnumUtil.getByCode(FluxProcessDirection.class, code);
    }

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
