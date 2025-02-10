package com.anypluspay.payment.facade.instant;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.asset.FluxInstructionExecutor;
import com.anypluspay.payment.domain.asset.FluxResult;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.InstructionType;
import com.anypluspay.payment.domain.payorder.PayOrderStatus;
import com.anypluspay.payment.facade.InstantPaymentFacade;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.request.TradeInfo;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.asset.BalanceAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Currency;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author wxj
 * 2025/2/7
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MultiMethodPayTest extends InstPaymentBaseTest {

    @Autowired
    private InstantPaymentFacade instantPaymentFacade;

    @MockitoBean("BALANCE_FluxInstructExecutor")
    private FluxInstructionExecutor fluxInstructionExecutor;

    /**
     * 渠道失败
     */
    @Test
    public void testChannelFail() {
        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(randomId());
        request.setMerchantId("merchantId123");
        request.setPayAmount(new Money(2000, Currency.getInstance("CNY")));
        request.setPayerId(PAYER_MEMBER_ID);

        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, 1000), buildBalanceFundDetail(PAYER_MEMBER_ID, PAYER_ACCOUNT_NO, 2000)));

        when(fluxInstructionExecutor.execute(any(), any())).then(invocation -> {
            FluxInstruction fluxInstruction = invocation.getArgument(1);
            FluxResult result = new FluxResult();
            if (fluxInstruction.getType() == InstructionType.PAY) {
                result.setStatus(PayStatus.FAIL);
            } else {
                result.setStatus(PayStatus.SUCCESS);
            }
            return result;
        });

        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setTradeAmount(new Money(2000, Currency.getInstance("CNY")));
        tradeInfo.setTradeId("tradeId123");
        tradeInfo.setPayeeId("payee123");

        FundDetailInfo payeeFundDetail = new FundDetailInfo();
        payeeFundDetail.setMemberId("payee123");
        payeeFundDetail.setAmount(new Money(2000, Currency.getInstance("CNY")));
        payeeFundDetail.setAssetInfo(new BalanceAsset("payee123", "payee-account"));
        tradeInfo.setPayeeFundDetail(List.of(payeeFundDetail));
        request.setTradeInfos(List.of(tradeInfo));
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        Assert.assertEquals(PayOrderStatus.FAIL, response.getOrderStatus());
        assetPayment(request, response);
    }

}
