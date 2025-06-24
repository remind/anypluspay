package com.anypluspay.wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 钱包首页
 *
 * @author wxj
 * 2025/6/24
 */
@Controller
@RequestMapping("/")
public class IndexController extends AbstractController {

    /**
     * 钱包首页
     *
     * @return
     */
    @GetMapping
    public String index() {
        return "index";
    }
}
