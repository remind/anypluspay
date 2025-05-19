package com.anypluspay.admin.demo.cashier;

import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.admin.demo.cashier.request.PayMethod;
import com.anypluspay.admin.demo.cashier.request.PayRequest;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.facade.acquiring.AcquiringFacade;
import com.anypluspay.payment.facade.acquiring.TradeResponse;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayRequest;
import com.anypluspay.payment.facade.acquiring.pay.AcquiringPayResponse;
import com.anypluspay.payment.types.PaymentExtKey;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.paymethod.PayModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2025/4/2
 */
@Controller
@RequestMapping("/demo/cashier")
public class PayCashierController {

    @Autowired
    private AcquiringFacade acquiringFacade;

    @Autowired
    private OuterAccountManagerFacade outerAccountManagerFacade;

    /**
     * 收银台
     *
     * @param paymentId
     * @return
     */
    @GetMapping
    public String cashier(@RequestParam String paymentId, Model model) {
        TradeResponse tradeResponse = acquiringFacade.queryByPaymentId(paymentId);
        model.addAttribute("order", tradeResponse);

        OuterAccountResponse accountResponse = outerAccountManagerFacade.queryByMemberAndAccountTypeId(tradeResponse.getPayerId(), "101");
        model.addAttribute("availableBalance", accountResponse.getAvailableBalance());

        return "demo/cashier/pay";
    }

    /**
     * 支付
     *
     * @param request
     * @return
     */
    @PostMapping("/pay")
    @ResponseBody
    public ResponseResult<AcquiringPayResponse> pay(@RequestBody PayRequest request) {
        TradeResponse tradeResponse = acquiringFacade.queryByPaymentId(request.getPaymentId());
        AcquiringPayRequest acquiringPayRequest = new AcquiringPayRequest();
        acquiringPayRequest.setPaymentId(tradeResponse.getPaymentId());
        acquiringPayRequest.setPayerFundDetail(buildPayerFundDetail(tradeResponse.getPayerId(), tradeResponse.getAmount(), request.getPayMethods()));
        return ResponseResult.success(acquiringFacade.pay(acquiringPayRequest));
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
