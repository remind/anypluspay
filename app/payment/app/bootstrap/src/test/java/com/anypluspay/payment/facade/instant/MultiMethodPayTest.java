package com.anypluspay.payment.facade.instant;

import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.payorder.PayOrderStatus;
import com.anypluspay.payment.facade.InstantPaymentFacade;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.request.TradeInfo;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.types.asset.BalanceAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Currency;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * 多个支付方式测试
 *
 * @author wxj
 * 2025/2/7
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MultiMethodPayTest extends InstPaymentBaseTest {

    @Autowired
    private InstantPaymentFacade instantPaymentFacade;

    /**
     * 渠道 + 余额支付，但余额失败，渠道退款
     */
    @Test
    public void testBalanceFail() {
        mockFundInSuccess();
        mockRefundSuccess();
        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(randomId());
        request.setMerchantId("merchantId123");
        request.setPayAmount(new Money(2000, Currency.getInstance("CNY")));
        request.setPayerId(PAYER_MEMBER_ID);
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, 1000), buildBalanceFundDetail(PAYER_MEMBER_ID, PAYER_ACCOUNT_NO, 2000)));
        doAnswer(invocation -> {
            AccountingRequest accountingRequest = invocation.getArgument(0);
            long c = accountingRequest.getEntryDetails().stream().filter(entryDetail -> entryDetail.getAccountNo().equals(PAYER_ACCOUNT_NO) && entryDetail.getCrDr() == CrDr.DEBIT).count();
            if (c == 1) {
                throw new BizException("accounting error");
            }
            return null;
        }).when(accountingFacade).apply(any());

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
