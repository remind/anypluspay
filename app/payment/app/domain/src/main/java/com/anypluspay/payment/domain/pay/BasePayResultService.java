package com.anypluspay.payment.domain.pay;

import com.anypluspay.payment.domain.pay.pay.PayOrderResultService;
import com.anypluspay.payment.domain.pay.refund.RefundResultService;
import com.anypluspay.payment.domain.service.IdGeneratorService;
import com.anypluspay.payment.types.IdType;
import com.anypluspay.payment.types.PayResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付结果处理
 *
 * @author wxj
 * 2025/5/23
 */
@Service
@Slf4j
public class BasePayResultService {

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private PayOrderResultService payOrderResultService;

    @Autowired
    private RefundResultService refundResultService;

    public void process(String payOrderId, PayResult payResult) {
        log.info("支付指令单结果处理,payOrderId={},payResult={}", payOrderId, payResult);
        IdType bizOrderIdType = idGeneratorService.getIdType(payOrderId);
        if (bizOrderIdType == IdType.PAY_ORDER_ID) {
            payOrderResultService.processFluxResult(payOrderId, payResult);
        } else if (bizOrderIdType == IdType.REFUND_ORDER_ID) {
            refundResultService.processFluxResult(payOrderId, payResult);
        } else {
            log.error("支付指令单结果处理,payOrderId={},success={},未知的支付指令单类型", payOrderId, payResult);
        }
    }
}
