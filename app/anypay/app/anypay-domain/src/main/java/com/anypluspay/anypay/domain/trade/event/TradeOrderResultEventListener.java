package com.anypluspay.anypay.domain.trade.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author wxj
 * 2026/2/14
 */
@Component
public class TradeOrderResultEventListener {

    @TransactionalEventListener(fallbackExecution = true)
    public void listen(TradeOrderResultEvent event) {

    }
}
