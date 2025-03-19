package com.anypluspay.testtrade.facade;

import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.facade.InstantPaymentFacade;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.status.GeneralPayOrderStatus;
import com.anypluspay.testtrade.facade.request.TradeInfo;
import com.anypluspay.testtrade.facade.response.TradeResponse;
import com.anypluspay.testtrade.infra.persistence.dataobject.PayOrderDO;
import com.anypluspay.testtrade.infra.persistence.dataobject.TradeOrderDO;
import com.anypluspay.testtrade.infra.persistence.mapper.PayOrderMapper;
import com.anypluspay.testtrade.infra.persistence.mapper.TradeOrderMapper;
import com.anypluspay.testtrade.facade.request.PayRequest;
import com.anypluspay.testtrade.facade.response.PayResponse;
import com.anypluspay.testtrade.types.PayStatus;
import com.anypluspay.testtrade.types.TradeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 交易
 * @author wxj
 * 2025/3/18
 */
@RestController
public class TradeFacadeImpl implements TradeFacade {

    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private OuterAccountManagerFacade outerAccountManagerFacade;

    @Autowired
    private InstantPaymentFacade instantPaymentFacade;

    @Override
    public TradeResponse create(TradeInfo tradeInfo) {
        TradeOrderDO tradeOrderDO = saveTrade(tradeInfo);
        TradeResponse response = new TradeResponse();
        response.setTradeId(String.valueOf(tradeOrderDO.getId()));
        response.setStatus(tradeOrderDO.getStatus());
        return response;
    }

    @Override
    public PayResponse pay(PayRequest payRequest) {
        TradeOrderDO tradeOrderDO = tradeOrderMapper.selectById(payRequest.getTradeId());
        PayOrderDO payOrderDO = savePayOrder(payRequest);
        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(String.valueOf(payOrderDO.getId()));
        request.setMerchantId(tradeOrderDO.getMerchantId());
        request.setPayerId(tradeOrderDO.getPayerId());
        request.setPayAmount(new Money(tradeOrderDO.getAmount()));
        request.setPayeeFundDetail(buildPayeeFundDetail(tradeOrderDO));
        request.setPayerFundDetail(buildPayerFundDetail(tradeOrderDO));
        InstantPaymentResponse instantPaymentResponse = instantPaymentFacade.pay(request);
        if (instantPaymentResponse.getOrderStatus() == GeneralPayOrderStatus.SUCCESS) {
            tradeOrderDO.setStatus(TradeStatus.SUCCESS.getCode());
            payOrderDO.setStatus(PayStatus.SUCCESS.getCode());
            tradeOrderMapper.updateById(tradeOrderDO);
            payOrderMapper.updateById(payOrderDO);
        }
        PayResponse response = new PayResponse();
        response.setTradeId(String.valueOf(tradeOrderDO.getId()));
        response.setStatus(tradeOrderDO.getStatus());
        response.setMessage(instantPaymentResponse.getResult().getResultMessage());
        return response;
    }

    private TradeOrderDO saveTrade(TradeInfo tradeInfo) {
        TradeOrderDO tradeOrderDO = new TradeOrderDO();
        tradeOrderDO.setSubject(tradeInfo.getSubject());
        tradeOrderDO.setGoodsDesc(tradeInfo.getGoodsDesc());
        tradeOrderDO.setAmount(new BigDecimal(tradeInfo.getAmount()));
        tradeOrderDO.setStatus(TradeStatus.INIT.getCode());
        tradeOrderDO.setMerchantId(tradeInfo.getMerchantId());
        tradeOrderDO.setPayeeId(tradeInfo.getPayeeId());
        tradeOrderDO.setPayeeAccount(getBaseAccountNo(tradeInfo.getPayeeId()));
        tradeOrderDO.setPayerId(tradeInfo.getPayerId());
        tradeOrderMapper.insert(tradeOrderDO);
        return tradeOrderDO;
    }

    private String getBaseAccountNo(String memberId) {
        OuterAccountResponse accountResponse = outerAccountManagerFacade.queryByMemberAndAccountTypeId(memberId, "101");
        return accountResponse.getAccountNo();
    }

    private PayOrderDO savePayOrder(PayRequest payRequest) {
        PayOrderDO payOrderDO = new PayOrderDO();
        payOrderDO.setTradeId(payRequest.getTradeId());
        payOrderDO.setStatus(PayStatus.INIT.getCode());
        payOrderMapper.insert(payOrderDO);
        return payOrderDO;
    }

    private List<FundDetailInfo> buildPayeeFundDetail(TradeOrderDO tradeOrderDO) {
        List<FundDetailInfo> payeeFundDetail = new ArrayList<>();
        FundDetailInfo fundDetailInfo = new FundDetailInfo();
        fundDetailInfo.setAmount(new Money(tradeOrderDO.getAmount()));
        fundDetailInfo.setMemberId(tradeOrderDO.getPayeeId());
        BalanceAsset balanceAsset = new BalanceAsset(tradeOrderDO.getPayeeId(), tradeOrderDO.getPayeeAccount());
        fundDetailInfo.setAssetTypeCode(balanceAsset.getAssetType().getCode());
        fundDetailInfo.setAssetJsonStr(balanceAsset.toJsonStr());
        payeeFundDetail.add(fundDetailInfo);
        return payeeFundDetail;
    }

    private List<FundDetailInfo> buildPayerFundDetail(TradeOrderDO tradeOrderDO) {
        List<FundDetailInfo> payeeFundDetail = new ArrayList<>();
        FundDetailInfo fundDetailInfo = new FundDetailInfo();
        fundDetailInfo.setAmount(new Money(tradeOrderDO.getAmount()));
        fundDetailInfo.setMemberId(tradeOrderDO.getPayerId());
        BalanceAsset balanceAsset = new BalanceAsset(tradeOrderDO.getPayerId(), getBaseAccountNo(tradeOrderDO.getPayerId()));
        fundDetailInfo.setAssetTypeCode(balanceAsset.getAssetType().getCode());
        fundDetailInfo.setAssetJsonStr(balanceAsset.toJsonStr());
        payeeFundDetail.add(fundDetailInfo);
        return payeeFundDetail;
    }

}
