package com.anypluspay.anypay.domain.pay.processor;

import com.anypluspay.anypay.domain.pay.PayMethod;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.repository.PayMethodRepository;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2026/2/3
 */
@Component
public class ProcessorExecutor {

    @Resource
    private PayMethodRepository payMethodRepository;

    @Autowired
    private ApplicationContext applicationContext;

    public void pay(TradeOrder tradeOrder, PayOrder payOrder) {
        PayMethod payMethod = payMethodRepository.load(payOrder.getPayMethod());
        PayProcessor payProcessor = applicationContext.getBean(payMethod.getProcessor() + "PayProcessor", PayProcessor.class);
        payProcessor.pay(tradeOrder, payOrder, payMethod);
    }

    public void refund(TradeOrder tradeOrder, PayOrder payOrder, PayOrder originPayOrder) {
        PayMethod payMethod = payMethodRepository.load(payOrder.getPayMethod());
        PayProcessor payProcessor = applicationContext.getBean(payMethod.getProcessor() + "PayProcessor", PayProcessor.class);
        payProcessor.refund(tradeOrder, payOrder, originPayOrder);
    }

}
