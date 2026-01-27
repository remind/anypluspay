package com.anypluspay.anypay.application.trade;

import com.anypluspay.anypay.application.trade.builder.TradeBuilder;
import com.anypluspay.anypay.domain.trade.TradeOrder;
import com.anypluspay.anypay.domain.trade.repository.TradeOrderRepository;
import com.anypluspay.anypay.types.request.UnifiedOrderRequest;
import com.anypluspay.anypay.types.trade.TradeType;
import com.anypluspay.commons.exceptions.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 即时到账交易服务
 *
 * @author wxj
 * 2026/1/26
 */
@Service
public class InstantTradeService {

    @Autowired
    private TradeBuilder tradeBuilder;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    /**
     * 创建交易
     *
     * @param request
     * @return
     */
    public String create(UnifiedOrderRequest request) {
        TradeOrder tradeOrder = tradeBuilder.buildTradeOrder(request, TradeType.INSTANT_ACQUIRING);
        transactionTemplate.executeWithoutResult(status -> {
            try {
                tradeOrderRepository.store(tradeOrder);
            } catch (DuplicateKeyException e) {
                throw new BizException("外部交易号重复");
            }
        });
        return tradeOrder.getTradeOrderId();
    }

}
