package com.anypluspay.payment.domain.payorder.general;

import com.anypluspay.payment.domain.deposit.DepositService;
import com.anypluspay.payment.domain.payorder.event.PayOrderResultEvent;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayOrderType;
import com.anypluspay.payment.types.status.GeneralPayOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 支付单结果通知
 *
 * @author wxj
 * 2025/5/15
 */
@Service
public class PayOrderResultNotifyService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private DepositService depositService;

    public void process(GeneralPayOrder generalPayOrder) {
        IdType bizOrderIdType = idGeneratorService.getIdType(generalPayOrder.getPaymentId());
        if (bizOrderIdType == IdType.DEPOSIT_ORDER_ID) {
            depositService.processResult(generalPayOrder.getPaymentId(), generalPayOrder.getOrderStatus());
        } else {
            if (generalPayOrder.getOrderStatus() == GeneralPayOrderStatus.SUCCESS
                    || generalPayOrder.getOrderStatus() == GeneralPayOrderStatus.FAIL) {
                applicationContext.publishEvent(new PayOrderResultEvent(generalPayOrder.getOrderId(), PayOrderType.PAY));
            }
        }
    }
}
