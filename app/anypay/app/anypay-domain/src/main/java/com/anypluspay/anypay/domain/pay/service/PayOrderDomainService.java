package com.anypluspay.anypay.domain.pay.service;

import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.processor.ProcessorExecutor;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.types.PayResult;
import com.anypluspay.anypay.types.common.PayOrderStatus;
import com.anypluspay.anypay.types.trade.TradeOrderStatus;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author wxj
 * 2026/1/29
 */
@Slf4j
@Service
public class PayOrderDomainService {

    @Resource
    private PayOrderRepository payOrderRepository;

    @Resource
    private ProcessorExecutor processorExecutor;

    @Resource
    private TransactionTemplate transactionTemplate;

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

    public PayResult refund(TradeOrder refundTradeOrder, List<PayOrder> refundPayOrders, List<PayOrder> originPayOrders) {
        PayResult payResult = new PayResult();
        payResult.setTradeId(refundTradeOrder.getTradeId());
        for (int i = 0; i < refundPayOrders.size(); i++) {
            PayOrder refundPayOrder = refundPayOrders.get(i);
            processorExecutor.refund(refundTradeOrder, refundPayOrder
                    , originPayOrders.stream().filter(po -> po.getPayId().equals(refundPayOrder.getPayId())).findFirst().get());
            payOrderRepository.reStore(refundPayOrder);
            if (refundPayOrder.getStatus() == PayOrderStatus.SUCCESS && i == refundPayOrders.size() - 1) {
                payResult.setStatus(TradeOrderStatus.SUCCESS);
            } else if (refundPayOrder.getStatus() == PayOrderStatus.FAIL) {
                payResult.setStatus(TradeOrderStatus.FAIL);
                payResult.setResultMsg(refundPayOrder.getResultMsg());
                payResult.setResultCode(refundPayOrder.getResultCode());
                if (i != 0 && refundPayOrders.size() > 1) {
                    log.warn("多笔退款，部分成功，部分失败, tradeId={}", refundTradeOrder.getTradeId());
                }
                break;
            } else {
                payResult.setStatus(TradeOrderStatus.WAIT_PAY);
            }
        }
        return payResult;
    }

}
