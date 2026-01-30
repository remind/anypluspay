package com.anypluspay.anypay.openapi.controller;

import com.anypluspay.anypay.application.pay.PaymentService;
import com.anypluspay.anypay.domain.channel.ChannelFactoryService;
import com.anypluspay.anypay.domain.channel.spi.response.ChannelNotifyResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 渠道回调接口
 *
 * @author wxj
 * 2026/1/30
 */
@Slf4j
@Controller
@RequestMapping("/callback")
public class ChannelCallbackController {

    @Resource
    private ChannelFactoryService channelFactoryService;

    @Resource
    private PaymentService paymentService;

    /**
     * 异步通知
     *
     * @param channelCode 渠道编码
     * @param request     通知参数
     * @return
     */
    @ResponseBody
    @PostMapping(value = {"/notify/{channelCode}"})
    public String notify(@Validated @NotBlank @PathVariable String channelCode, HttpServletRequest request) {
        ChannelNotifyResponse channelResponse = channelFactoryService.channelCallback(channelCode).notify(channelCode, request);
        paymentService.processChannelResult(channelCode, channelResponse);
        return channelResponse.getBody();
    }
}
