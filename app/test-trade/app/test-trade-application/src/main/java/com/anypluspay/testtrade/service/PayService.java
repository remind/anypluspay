package com.anypluspay.testtrade.service;

import com.anypluspay.testtrade.infra.persistence.dataobject.PayOrderDO;
import com.anypluspay.testtrade.infra.persistence.dataobject.TradeOrderDO;
import com.anypluspay.testtrade.infra.persistence.mapper.PayOrderMapper;
import com.anypluspay.testtrade.infra.persistence.mapper.TradeOrderMapper;
import com.anypluspay.testtrade.types.PayStatus;
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
    private TransactionTemplate ttransactionTemplate;

    public void processResult(Long payOrderId, PayStatus payStatus) {
        ttransactionTemplate.executeWithoutResult(status -> {
            PayOrderDO payOrderDO = payOrderMapper.lockById(payOrderId);
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
}
