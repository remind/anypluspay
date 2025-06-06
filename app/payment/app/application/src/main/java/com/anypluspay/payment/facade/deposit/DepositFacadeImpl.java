package com.anypluspay.payment.facade.deposit;

import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.commons.lang.BaseResult;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.response.GlobalResultCode;
import com.anypluspay.payment.application.AbstractTradeService;
import com.anypluspay.payment.domain.PayChannelParamService;
import com.anypluspay.payment.domain.pay.pay.PayOrder;
import com.anypluspay.payment.domain.trade.deposit.DepositOrder;
import com.anypluspay.payment.domain.repository.DepositOrderRepository;
import com.anypluspay.payment.types.PayResult;
import com.anypluspay.payment.types.PayStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.RestController;

/**
 * 充值服务
 *
 * @author wxj
 * 2025/5/14
 */
@RestController
@Slf4j
public class DepositFacadeImpl extends AbstractTradeService implements DepositFacade {

    @Autowired
    private DepositBuilder depositBuilder;

    @Autowired
    private DepositOrderRepository depositOrderRepository;

    @Autowired
    private PayChannelParamService payChannelParamService;

    @Override
    public DepositResponse apply(DepositRequest request) {
        String paymentId = "";
        DepositResponse response;
        try {
            DepositOrder depositOrder = depositBuilder.buildDepositOrder(request);
            PayOrder payOrder = depositBuilder.buildPayProcess(depositOrder, request.getPayerFundDetail());
            transactionTemplate.executeWithoutResult(status -> {
                depositOrderRepository.store(depositOrder);
                payOrderRepository.store(payOrder);
            });
            PayResult result = payOrderService.process(payOrder);
            paymentId = depositOrder.getTradeId();
            response = processResult(depositOrder.getTradeId(), result);
        } catch (TransactionException e) {
            log.error("充值异常", e);
            response = buildExceptionResponse(paymentId, e);
        }
        return response;
    }

    private DepositResponse processResult(String paymentId, PayResult result) {
        DepositOrder depositOrder = depositOrderRepository.load(paymentId);
        DepositResponse response = new DepositResponse();
        response.setSuccess(true);
        if (depositOrder != null) {
            response.setTradeId(paymentId);
            response.setOrderStatus(depositOrder.getStatus().getCode());
        }

        if (result != null) {
            if (result.getPayStatus() == PayStatus.PROCESS && depositOrder != null) {
                Extension payResponse = new Extension(payChannelParamService.get(depositOrder.getOrderId()));
                response.setIrd(payResponse.get(ChannelExtKey.INST_REDIRECTION_DATA.getCode()));
            }
            response.setResultCode(result.getResultCode());
            response.setResultMsg(result.getResultMessage());
        } else {
            response.setResultCode(GlobalResultCode.SUCCESS.getCode());
        }
        return response;
    }

    public DepositResponse buildExceptionResponse(String paymentId, Exception e) {
        DepositResponse response = processResult(paymentId, null);
        BaseResult.fillExceptionResult(response, e);
        return response;
    }
}
