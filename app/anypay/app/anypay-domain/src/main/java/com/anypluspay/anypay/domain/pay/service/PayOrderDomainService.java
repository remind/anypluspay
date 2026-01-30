package com.anypluspay.anypay.domain.pay.service;

import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.types.common.PayOrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author wxj
 * 2026/1/29
 */
@Service
public class PayOrderDomainService {


    public void paySuccess(PayOrder payOrder) {
        Assert.isTrue(payOrder.getStatus() == PayOrderStatus.PAYING, "仅支付中才能支付成功");
        payOrder.setStatus(PayOrderStatus.SUCCESS);
    }

    public void payFail(PayOrder payOrder) {
        Assert.isTrue(payOrder.getStatus() == PayOrderStatus.PAYING, "仅支付中才能支付失败");
        payOrder.setStatus(PayOrderStatus.FAIL);
    }

    public void paying(PayOrder payOrder) {
        Assert.isTrue(payOrder.getStatus() == PayOrderStatus.INIT || payOrder.getStatus() == PayOrderStatus.PAYING, "仅支付初始化或支付中才能到支付中");
        payOrder.setStatus(PayOrderStatus.PAYING);
    }

}
