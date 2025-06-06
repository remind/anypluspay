package com.anypluspay.payment.application;

import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.asset.AssetInfo;
import com.anypluspay.payment.types.asset.BelongTo;
import com.anypluspay.payment.types.funds.FundAction;
import com.anypluspay.payment.types.funds.FundDetail;
import com.anypluspay.payment.types.paymethod.PayModel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2024/1/15
 */
public abstract class TradeBuilder {

    @Autowired
    protected IdGeneratorService idGeneratorService;

    /**
     * 构造资金明细
     * @param tradeId
     * @param orderId
     * @param info
     * @param belongTo
     * @return
     */
    protected FundDetail buildFundDetail(String tradeId, String orderId, FundDetailInfo info, BelongTo belongTo) {
        FundDetail fundDetail = new FundDetail();
        fundDetail.setTradeId(tradeId);
        fundDetail.setOrderId(orderId);
        fundDetail.setDetailId(idGeneratorService.genIdByRelateId(tradeId, IdType.FUND_DETAIL_ID));
        fundDetail.setAmount(info.getAmount());
        fundDetail.setMemberId(info.getMemberId());
        fundDetail.setAssetInfo(AssetInfo.parse(info.getAssetTypeCode(), info.getAssetJsonStr()));
        fundDetail.setBelongTo(belongTo);
        fundDetail.setFundAction(belongTo == BelongTo.PAYER ? FundAction.DECREASE : FundAction.INCREASE);
        fundDetail.setPayModel(EnumUtil.getByCode(PayModel.class, info.getPayModel()));
        fundDetail.setPayParam(new Extension(info.getPayParam()));
        fundDetail.setExtension(new Extension(info.getExtension()));
        return fundDetail;
    }

}
