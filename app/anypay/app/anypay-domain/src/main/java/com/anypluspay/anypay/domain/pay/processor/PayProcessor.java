package com.anypluspay.anypay.domain.pay.processor;

import com.anypluspay.anypay.domain.pay.PayMethod;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.trade.TradeOrder;

/**
 * @author wxj
 * 2026/2/3
 */
public interface PayProcessor {

    void pay(TradeOrder tradeOrder, PayOrder payOrder, PayMethod payMethod);

    void refund(TradeOrder tradeOrder, PayOrder payOrder, PayOrder originPayOrder);
}
