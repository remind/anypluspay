package com.anypluspay.testbank.controller;

import com.anypluspay.testbank.SystemConfig;
import com.anypluspay.testbank.controller.dto.OnlineBankPayDto;
import com.anypluspay.testbank.controller.dto.RefundDto;
import com.anypluspay.testbank.controller.request.PaySubmitRequest;
import com.anypluspay.testbank.persistence.dataobject.PayOrderDO;
import com.anypluspay.testbank.persistence.dataobject.RefundOrderDO;
import com.anypluspay.testbank.service.OnlineBankPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 网银支付
 *
 * @author wxj
 * 2024/11/25
 */
@Controller
@RequestMapping("/online-bank")
public class OnlineBankController {

    @Autowired
    private OnlineBankPayService onlineBankPayService;

    @Autowired
    private SystemConfig systemConfig;

    @PostMapping("/pay")
    public String pay(@ModelAttribute OnlineBankPayDto onlineBankPayDto, Model model) {
        PayOrderDO payOrderDO = onlineBankPayService.createOrder(onlineBankPayDto);
        model.addAttribute("payOrder", payOrderDO);
        model.addAttribute("testBankUrl", systemConfig.getTestBankUrl());
        return "pay";
    }

    @PostMapping("/submit")
    public String pay(@ModelAttribute PaySubmitRequest request, Model model) {
        onlineBankPayService.pay(request.getOutTradeNo(), request.getStatus());
        PayOrderDO payOrderDO = onlineBankPayService.getByOutTradeNo(request.getOutTradeNo());
        model.addAttribute("payOrder", payOrderDO);
        return "submit-result";
    }

    @GetMapping("/query")
    @ResponseBody
    public PayOrderDO pay(@RequestParam String outTradeNo) {
        return onlineBankPayService.getByOutTradeNo(outTradeNo);
    }

    @PostMapping("/refund")
    @ResponseBody
    public Map<String, String> refund(@RequestBody RefundDto refundDto) {
        RefundOrderDO refundOrderDO = onlineBankPayService.refund(refundDto);
        Map<String, String> response = new HashMap<>();
        response.put("outRequestNo", refundOrderDO.getOutRequestNo());
        response.put("refundId", refundOrderDO.getId().toString());
        response.put("status", refundOrderDO.getStatus());
        response.put("amount", refundOrderDO.getAmount().toString());
        return response;
    }

}
