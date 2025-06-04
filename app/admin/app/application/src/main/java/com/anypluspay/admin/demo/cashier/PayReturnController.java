package com.anypluspay.admin.demo.cashier;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 支付同步返回页面
 * @author wxj
 * 2025/6/4
 */
@Controller
@RequestMapping("/demo/pay/return")
public class PayReturnController {

    @GetMapping
    @PostMapping
    public String payReturn() {
        return "pay_return";
    }
}
