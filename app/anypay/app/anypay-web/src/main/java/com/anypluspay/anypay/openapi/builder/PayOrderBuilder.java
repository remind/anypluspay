package com.anypluspay.anypay.openapi.builder;

import cn.hutool.core.lang.UUID;
import com.anypluspay.anypay.domain.common.service.IdGeneratorService;
import com.anypluspay.anypay.domain.pay.PayMethod;
import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.openapi.request.PaySubmitRequest;
import com.anypluspay.anypay.types.common.IdType;
import com.anypluspay.anypay.types.common.PayOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2026/1/29
 */
@Component
public class PayOrderBuilder {

    @Autowired
    private IdGeneratorService idGeneratorService;

    public PayOrder build(PaySubmitRequest request, PayMethod payMethod, TradeOrder tradeOrder) {
        PayOrder payOrder = new PayOrder();
        payOrder.setPayId(idGeneratorService.genIdByRelateId(tradeOrder.getTradeId(), IdType.PAY_ORDER_ID));
        payOrder.setTradeId(tradeOrder.getTradeId());
        payOrder.setPayMethod(request.getPayMethod());
        payOrder.setPayParam(request.getPayParam());
        payOrder.setAmount(tradeOrder.getAmount());
        payOrder.setStatus(PayOrderStatus.INIT);
        payOrder.setChannelCode(payMethod.getChannelCode());
        payOrder.setChannelRequestNo(UUID.fastUUID().toString(true));
        return payOrder;
    }
}
