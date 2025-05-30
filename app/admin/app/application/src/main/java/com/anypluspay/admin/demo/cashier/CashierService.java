package com.anypluspay.admin.demo.cashier;

import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.admin.demo.cashier.request.DepositPayRequest;
import com.anypluspay.admin.demo.cashier.request.PayMethod;
import com.anypluspay.admin.demo.cashier.request.PayRequest;
import com.anypluspay.admin.demo.cashier.response.WebPayMethodResponse;
import com.anypluspay.channel.types.ChannelExtKey;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                OuterAccountResponse accountResponse = outerAccountManagerFacade.queryByMemberAndAccountTypeId(payerId, payMethod.getPayInst());
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
    public Map<String, String> acquiringPay(PayRequest request) {
        List<PayMethod> payMethods = convertPayMethods(CashierType.ACQUIRING, request.getPayMethods());
        TradeResponse tradeResponse = acquiringFacade.queryByPaymentId(request.getPaymentId());
        AcquiringPayRequest acquiringPayRequest = new AcquiringPayRequest();
        acquiringPayRequest.setPaymentId(tradeResponse.getPaymentId());
        acquiringPayRequest.setPayerFundDetail(buildPayerFundDetail(tradeResponse.getPayerId(), tradeResponse.getAmount(), payMethods));
        AcquiringPayResponse response = acquiringFacade.pay(acquiringPayRequest);
        Map<String, String> result = new HashMap<>();
        if (response.isSuccess()) {
            result.put("payStatus", response.getOrderStatus());
            result.put("message", response.getResultMsg());
            result.put("instUrl", response.getInstUrl());
        } else {
            result.put("payStatus", response.getResultCode());
            result.put("message", response.getResultMsg());
            result.put("instUrl", response.getInstUrl());
        }
        return result;
    }

    /**
     * 充值支付提交
     * @param request
     * @return
     */
    public Map<String, String> depositPay(DepositPayRequest request) {
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setMemberId(request.getMemberId());
        depositRequest.setAccountNo(request.getAccountNo());
        depositRequest.setAmount(new Money(request.getAmount()));
        depositRequest.setMemo(request.getMemo());
        List<PayMethod> payMethods = convertPayMethods(CashierType.DEPOSIT, request.getPayMethods());
        List<FundDetailInfo> fundDetailInfos = buildPayerFundDetail(depositRequest.getMemberId(), depositRequest.getAmount(), payMethods);
        depositRequest.setPayerFundDetail(fundDetailInfos);
        DepositResponse depositResponse = depositFacade.apply(depositRequest);
        Map<String, String> result = new HashMap<>();
        result.put("payStatus", depositResponse.getResult().getPayStatus().getCode());
        result.put("message", depositResponse.getResult().getResultMessage());
        Extension payResponse = new Extension(depositResponse.getResult().getPayResponse());
        result.put("instUrl", payResponse.get(ChannelExtKey.INST_URL.getCode()));
        return result;
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
        OuterAccountResponse accountResponse = outerAccountManagerFacade.queryByMemberAndAccountTypeId(memberId, "101");
        return accountResponse.getAccountNo();
    }
}
