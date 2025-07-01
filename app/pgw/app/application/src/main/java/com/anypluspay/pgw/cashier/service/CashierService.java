package com.anypluspay.pgw.cashier.service;

import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.pgw.cashier.model.CashierConfig;
import com.anypluspay.pgw.cashier.model.CashierPayResult;
import com.anypluspay.pgw.cashier.model.CashierType;
import com.anypluspay.pgw.cashier.request.DepositPayRequest;
import com.anypluspay.pgw.cashier.request.PayMethod;
import com.anypluspay.pgw.cashier.request.PayRequest;
import com.anypluspay.pgw.cashier.response.WebPayMethodResponse;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.facade.acquiring.AcquiringFacade;
import com.anypluspay.payment.facade.acquiring.TradeResponse;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayRequest;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayResponse;
import com.anypluspay.payment.facade.deposit.DepositFacade;
import com.anypluspay.payment.facade.deposit.DepositRequest;
import com.anypluspay.payment.facade.deposit.DepositResponse;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.types.PaymentExtKey;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.paymethod.PayModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2025/5/20
 */
@Service
public class CashierService {

    @Autowired
    private AcquiringFacade acquiringFacade;

    @Autowired
    private DepositFacade depositFacade;

    @Autowired
    private OuterAccountManagerFacade outerAccountManagerFacade;

    public List<WebPayMethodResponse> buildPayMethods(CashierType cashierType, String payerId) {
        List<PayMethod> payMethods = cashierType == CashierType.DEPOSIT ? CashierConfig.getPayMethodsOfDeposit() : CashierConfig.getPayMethodsOfPay();
        return payMethods.stream().map(payMethod -> {
            WebPayMethodResponse payMethodResponse = new WebPayMethodResponse();
            payMethodResponse.setCode(payMethod.getCode());
            payMethodResponse.setName(payMethod.getName());
            if (payMethod.getPayModel().equals(PayModel.BALANCE.getCode())) {
                OuterAccountResponse accountResponse = outerAccountManagerFacade.queryByMemberIdAndAccountType(payerId, payMethod.getPayInst());
                payMethodResponse.setTitle("可用余额：" + accountResponse.getAvailableBalance());
            }
            return payMethodResponse;
        }).toList();
    }

    /**
     * 收单支付提交
     *
     * @param request
     */
    public CashierPayResult acquiringPay(PayRequest request) {
        List<PayMethod> payMethods = convertPayMethods(CashierType.ACQUIRING, request.getPayMethods());
        TradeResponse tradeResponse = acquiringFacade.queryByTradeId(request.getTradeId());
        AcquiringPayRequest acquiringPayRequest = new AcquiringPayRequest();
        acquiringPayRequest.setTradeId(tradeResponse.getTradeId());
        acquiringPayRequest.setPayerFundDetail(buildPayerFundDetail(tradeResponse.getPayerId(), tradeResponse.getAmount(), payMethods));
        AcquiringPayResponse response = acquiringFacade.pay(acquiringPayRequest);
        return getCashierPayResult(response);
    }

    private static CashierPayResult getCashierPayResult(AcquiringPayResponse response) {
        if (!response.isSuccess()) {
            throw new BizException(response.getResultCode(), response.getResultMsg());
        }
        CashierPayResult cashierPayResult = new CashierPayResult();
        cashierPayResult.setTradeId(response.getTradeId());
        cashierPayResult.setResultCode(response.getResultCode());
        cashierPayResult.setResultMsg(response.getResultMsg());
        cashierPayResult.setStatus(response.getOrderStatus());
        cashierPayResult.setIrd(response.getIrd());
        return cashierPayResult;
    }

    /**
     * 充值支付提交
     * @param request
     * @return
     */
    public CashierPayResult depositPay(DepositPayRequest request) {
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setPartnerId(request.getPartnerId());
        depositRequest.setMemberId(request.getMemberId());
        depositRequest.setAccountNo(request.getAccountNo());
        depositRequest.setAmount(new Money(request.getAmount()));
        depositRequest.setMemo(request.getMemo());
        List<PayMethod> payMethods = convertPayMethods(CashierType.DEPOSIT, request.getPayMethods());
        List<FundDetailInfo> fundDetailInfos = buildPayerFundDetail(depositRequest.getMemberId(), depositRequest.getAmount(), payMethods);
        depositRequest.setPayerFundDetail(fundDetailInfos);
        DepositResponse depositResponse = depositFacade.apply(depositRequest);
        return convertResult(depositResponse);
    }

    private CashierPayResult convertResult(DepositResponse response) {
        if (!response.isSuccess()) {
            throw new BizException(response.getResultCode(), response.getResultMsg());
        }
        CashierPayResult cashierPayResult = new CashierPayResult();
        cashierPayResult.setTradeId(response.getTradeId());
        cashierPayResult.setResultCode(response.getResultCode());
        cashierPayResult.setResultMsg(response.getResultMsg());
        cashierPayResult.setStatus(response.getOrderStatus());
        cashierPayResult.setIrd(response.getIrd());
        return cashierPayResult;
    }

    private List<PayMethod> convertPayMethods(CashierType cashierType, List<String> payMethods) {
        return payMethods.stream().map(payMethod -> {
            if (cashierType == CashierType.DEPOSIT) {
                return CashierConfig.getPayMethodOfDeposit(payMethod);
            } else {
                return CashierConfig.getPayMethodOfPay(payMethod);
            }
        }).toList();
    }

    private List<FundDetailInfo> buildPayerFundDetail(String payerId, Money amount, List<PayMethod> payMethods) {
        List<FundDetailInfo> payeeFundDetail = new ArrayList<>();

        // 为了方便测试，各个支付方式的金额平分
        Money[] amounts = amount.allocate(payMethods.size());
        for (int i = 0; i < payMethods.size(); i++) {
            PayMethod payMethod = payMethods.get(i);
            FundDetailInfo fundDetailInfo = new FundDetailInfo();
            fundDetailInfo.setAmount(amounts[i]);
            fundDetailInfo.setMemberId(payerId);
            fundDetailInfo.setPayModel(payMethod.getPayModel());
            fundDetailInfo.setAssetTypeCode(payMethod.getAssetType());
            if (payMethod.getPayModel().equals(PayModel.BALANCE.getCode())) {
                BalanceAsset balanceAsset = new BalanceAsset(payerId, getBaseAccountNo(payerId));
                fundDetailInfo.setAssetJsonStr(balanceAsset.toJsonStr());
            } else if (payMethod.getPayModel().equals(PayModel.ONLINE_BANK.getCode())) {
                Extension payParam = getPayParam(payMethod);
                fundDetailInfo.setPayParam(payParam.toJsonString());
            }
            payeeFundDetail.add(fundDetailInfo);
        }
        return payeeFundDetail;
    }

    private Extension getPayParam(PayMethod payMethod) {
        Extension payParam = new Extension();
        payParam.add(PaymentExtKey.PAY_INST.getCode(), payMethod.getPayInst());

        Extension instExt = new Extension();
        if (payMethod.getInstExtra() != null) {
            instExt.addAll(new Extension(payMethod.getInstExtra()));
        }
        instExt.add("ip", "127.0.0.1");
        payParam.add(PaymentExtKey.INST_EXT.getCode(), instExt.toJsonString());
        return payParam;
    }

    private String getBaseAccountNo(String memberId) {
        OuterAccountResponse accountResponse = outerAccountManagerFacade.queryByMemberIdAndAccountType(memberId, "101");
        return accountResponse.getAccountNo();
    }
}
