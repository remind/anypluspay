package com.anypluspay.anypay.domain.pay.service;

import com.anypluspay.anypay.domain.pay.PayOrder;
import com.anypluspay.anypay.domain.pay.builder.PayOrderBuilder;
import com.anypluspay.anypay.domain.pay.processor.ProcessorExecutor;
import com.anypluspay.anypay.domain.pay.repository.PayOrderRepository;
import com.anypluspay.anypay.domain.pay.validator.PayValidator;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.types.common.PayOrderStatus;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.Iterator;
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
    private PayValidator payValidator;

    @Resource
    private PayOrderBuilder payOrderBuilder;

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

    public void refund(TradeOrder refundTradeOrder, List<PayOrder> originPayOrders) {
        payValidator.refundValidate(refundTradeOrder, originPayOrders);
        List<PayOrder> payOrders = payOrderBuilder.buildRefundPayOrder(refundTradeOrder, originPayOrders);
        transactionTemplate.executeWithoutResult(status -> {
            payOrders.forEach(payOrder -> payOrderRepository.store(payOrder));
        });
        for (int i = 0; i < payOrders.size(); i++) {
            PayOrder payOrder = payOrders.get(i);
            processorExecutor.refund(refundTradeOrder, payOrder
                    , originPayOrders.stream().filter(po -> po.getPayId().equals(payOrder.getPayId())).findFirst().get());
            payOrderRepository.reStore(payOrder);
            if (payOrder.getStatus() == PayOrderStatus.SUCCESS && i == payOrders.size() - 1) {
                // 更新tradeOrder为成功
            } else if (payOrder.getStatus() == PayOrderStatus.FAIL) {
                // 跟新tradeOrder为失败
                if (i != 0 && payOrders.size() > 1) {
                    // 打印异常日志
                    log.warn("多笔退款，部分成功，部分失败, tradeId={}", refundTradeOrder.getTradeId());
                }
                break;
            }
        }
    }

}
