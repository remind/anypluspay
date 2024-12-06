package com.anypluspay.testbank.service;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.testbank.controller.dto.OnlineBankPayDto;
import com.anypluspay.testbank.domain.OrderStatus;
import com.anypluspay.testbank.persistence.dataobject.PayOrderDO;
import com.anypluspay.testbank.persistence.mapper.PayOrderMapper;
import com.anypluspay.testbank.persistence.mapper.RefundOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author wxj
 * 2024/11/26
 */
@Service
public class OnlineBankPayService {

    private final WebClient webClient = WebClient.builder().build();

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private RefundOrderMapper refundOrderMapper;

    public PayOrderDO createOrder(OnlineBankPayDto onlineBankPayDto) {
        PayOrderDO payOrderDO = new PayOrderDO();
        payOrderDO.setOutTradeNo(onlineBankPayDto.getOutTradeNo());
        payOrderDO.setSubject(onlineBankPayDto.getSubject());
        payOrderDO.setGoodsDesc(onlineBankPayDto.getGoodsDesc());
        payOrderDO.setAmount(onlineBankPayDto.getAmount());
        payOrderDO.setStatus(OrderStatus.WAIT_BUYER_PAY.name());
        payOrderDO.setNotifyUrl(onlineBankPayDto.getNotifyUrl());
        payOrderDO.setReturnUrl(onlineBankPayDto.getReturnUrl());
        payOrderMapper.insert(payOrderDO);
        return payOrderDO;
    }

    public void pay(String outTradeNo, String action) {
        PayOrderDO payOrderDO = getByOutTradeNo(outTradeNo);
        if ("1".equals(action)) {
            payOrderDO.setStatus(OrderStatus.TRADE_SUCCESS.name());
        } else if ("2".equals(action)) {
            payOrderDO.setStatus(OrderStatus.TRADE_CLOSED.name());
        }
        payOrderMapper.updateById(payOrderDO);
        notify(payOrderDO);
    }

    public PayOrderDO getByOutTradeNo(String outTradeNo) {
        LambdaQueryWrapper<PayOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrderDO::getOutTradeNo, outTradeNo);
        return payOrderMapper.selectOne(queryWrapper);
    }

    private void notify(PayOrderDO payOrderDO) {
        if (StrUtil.isNotBlank(payOrderDO.getNotifyUrl())) {
            webClient.post().uri(payOrderDO.getNotifyUrl()).bodyValue(payOrderDO).retrieve().bodyToMono(String.class).block();
        }
    }

}