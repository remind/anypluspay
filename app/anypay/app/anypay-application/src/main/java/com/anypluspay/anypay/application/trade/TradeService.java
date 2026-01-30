package com.anypluspay.anypay.application.trade;

import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.domain.trade.service.TradeOrderDomainService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 交易服务
 * @author wxj
 * 2026/1/30
 */
@Service
public class TradeService {

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private TradeOrderRepository tradeOrderRepository;

    @Resource
    private TradeOrderDomainService tradeOrderDomainService;

    public void close(String tradeId) {
        transactionTemplate.executeWithoutResult(status -> {
            TradeOrder tradeOrder = tradeOrderRepository.lock(tradeId);
            tradeOrderDomainService.close(tradeOrder);
            tradeOrderRepository.reStore(tradeOrder);
        });
    }

}
