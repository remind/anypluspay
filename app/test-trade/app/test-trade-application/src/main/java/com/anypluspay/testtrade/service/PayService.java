package com.anypluspay.testtrade.service;

import com.anypluspay.payment.types.status.GeneralPayOrderStatus;
import com.anypluspay.testtrade.infra.persistence.dataobject.PayOrderDO;
import com.anypluspay.testtrade.infra.persistence.dataobject.RefundOrderDO;
import com.anypluspay.testtrade.infra.persistence.dataobject.TradeOrderDO;
import com.anypluspay.testtrade.infra.persistence.mapper.PayOrderMapper;
import com.anypluspay.testtrade.infra.persistence.mapper.RefundOrderMapper;
import com.anypluspay.testtrade.infra.persistence.mapper.TradeOrderMapper;
import com.anypluspay.testtrade.types.PayStatus;
import com.anypluspay.testtrade.types.RefundStatus;
import com.anypluspay.testtrade.types.TradeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author wxj
 * 2025/4/10
 */
@Service
public class PayService {

    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private RefundOrderMapper refundOrderMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public void processResult(String payOrderId, PayStatus payStatus) {
        transactionTemplate.executeWithoutResult(status -> {
            PayOrderDO payOrderDO = payOrderMapper.lockById(payOrderId);
            if (payOrderDO == null) {
                return;
            }
            TradeOrderDO tradeOrderDO = tradeOrderMapper.lockById(payOrderDO.getTradeId());
            if (payStatus == PayStatus.SUCCESS) {
                tradeOrderDO.setStatus(TradeStatus.SUCCESS.getCode());
                payOrderDO.setStatus(PayStatus.SUCCESS.getCode());
            } else if (payStatus == PayStatus.FAIL) {
                tradeOrderDO.setStatus(TradeStatus.FAIL.getCode());
                payOrderDO.setStatus(PayStatus.FAIL.getCode());
            }
            tradeOrderMapper.updateById(tradeOrderDO);
            payOrderMapper.updateById(payOrderDO);
        });
    }

    public void processRefundResult(String refundOrderId, String paymentOrderStatus) {
        transactionTemplate.executeWithoutResult(status -> {
            RefundOrderDO refundOrderDO = refundOrderMapper.lockById(refundOrderId);
            if (refundOrderDO == null) {
                return;
            }
            if (paymentOrderStatus.equals(GeneralPayOrderStatus.SUCCESS.getCode())) {
                refundOrderDO.setStatus(RefundStatus.SUCCESS.getCode());
                refundOrderMapper.updateById(refundOrderDO);
            } else if (paymentOrderStatus.equals(GeneralPayOrderStatus.FAIL.getCode())) {
                refundOrderDO.setStatus(RefundStatus.FAIL.getCode());
                refundOrderMapper.updateById(refundOrderDO);
            }
        });
    }
}
