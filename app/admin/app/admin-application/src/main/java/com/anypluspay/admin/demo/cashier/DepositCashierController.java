package com.anypluspay.admin.demo.cashier;

import com.anypluspay.admin.demo.cashier.request.DepositCashierRequest;
import com.anypluspay.admin.demo.cashier.request.DepositPayRequest;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.payment.facade.deposit.DepositFacade;
import com.anypluspay.payment.facade.deposit.DepositRequest;
import com.anypluspay.payment.facade.deposit.DepositResponse;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.types.PaymentExtKey;
import com.anypluspay.payment.types.paymethod.PayModel;
import com.anypluspay.testtrade.facade.request.PayMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值
 * @author wxj
 * 2025/5/15
 */
@Controller
@RequestMapping("/demo/deposit-cashier")
public class DepositCashierController {

    @Autowired
    private DepositFacade depositFacade;

    /**
     * 进入充值收银台
     * @param request
     * @param model
     * @return
     */
    @PostMapping
    public String cashier(@ModelAttribute DepositCashierRequest request, Model model) {
        model.addAttribute("request", request);
        return "demo/cashier/deposit";
    }

    /**
     * 充值支付
     * @param request
     * @return
     */
    @PostMapping("/pay")
    @ResponseBody
    public ResponseResult<Map<String,String>> paySubmit(@RequestBody DepositPayRequest request) {
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setMemberId(request.getMemberId());
        depositRequest.setAccountNo(request.getAccountNo());
        depositRequest.setAmount(new Money(request.getAmount()));
        depositRequest.setMemo(request.getMemo());
        List<FundDetailInfo> fundDetailInfos = buildDepositPayerFundDetail(depositRequest.getMemberId(), depositRequest.getAmount(), request.getPayMethods());
        depositRequest.setPayerFundDetail(fundDetailInfos);
        DepositResponse depositResponse = depositFacade.apply(depositRequest);
        Map<String,String> result = new HashMap<>();
        result.put("payStatus", depositResponse.getResult().getPayStatus().getCode());
        result.put("message", depositResponse.getResult().getResultMessage());
        Extension payResponse = new Extension(depositResponse.getResult().getPayResponse());
        result.put("instUrl", payResponse.get(ChannelExtKey.INST_URL.getCode()));
        return ResponseResult.success(result);
    }

    private List<FundDetailInfo> buildDepositPayerFundDetail(String memberId, Money amount, List<PayMethod> payMethods) {
        List<FundDetailInfo> payeeFundDetail = new ArrayList<>();

        // 为了方便测试，各个支付方式的金额平分
        Money[] amounts = amount.allocate(payMethods.size());
        for (int i = 0; i < payMethods.size(); i++) {
            PayMethod payMethod = payMethods.get(i);
            FundDetailInfo fundDetailInfo = new FundDetailInfo();
            fundDetailInfo.setAmount(amounts[i]);
            fundDetailInfo.setMemberId(memberId);
            fundDetailInfo.setPayModel(payMethod.getPayModel());
            fundDetailInfo.setAssetTypeCode(payMethod.getAssetType());
            if (payMethod.getPayModel().equals(PayModel.ONLINE_BANK.getCode())) {
                Extension payParam = getPayParam(payMethod);
                fundDetailInfo.setPayParam(payParam.toJsonString());
            }
            payeeFundDetail.add(fundDetailInfo);
        }
        return payeeFundDetail;
    }

    private static Extension getPayParam(PayMethod payMethod) {
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
}
