package com.anypluspay.payment.facade;

import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.component.test.AbstractBaseTest;
import com.anypluspay.payment.application.instant.InstantPaymentService;
import com.anypluspay.payment.application.instant.request.FundDetailInfo;
import com.anypluspay.payment.application.instant.request.InstantPaymentRequest;
import com.anypluspay.payment.application.instant.request.TradeInfo;
import com.anypluspay.payment.types.asset.BalanceAsset;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Currency;
import java.util.List;

/**
 * @author wxj
 * 2024/1/18
 */
public class InstantPaymentServiceTest extends AbstractBaseTest {

    @Autowired
    private InstantPaymentService instantPaymentService;

    @Test
    public void testPay() {
        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(randomId());
        request.setMerchantId("123456");
        request.setPayAmount(new Money(1000, Currency.getInstance("CNY")));
        request.setPayerId("123456");

        FundDetailInfo fundDetailInfo = new FundDetailInfo();
        fundDetailInfo.setMemberId("123");
        fundDetailInfo.setAmount(new Money(1000, Currency.getInstance("CNY")));
        fundDetailInfo.setAssetInfo(new BalanceAsset("123", "1231"));
        request.setPayerFundDetail(List.of(fundDetailInfo));

        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setTradeAmount(new Money(1000, Currency.getInstance("CNY")));
        tradeInfo.setTradeId("tradeId123");
        tradeInfo.setPayeeId("123456");

        FundDetailInfo payeeFundDetail = new FundDetailInfo();
        payeeFundDetail.setMemberId("456");
        payeeFundDetail.setAmount(new Money(1000, Currency.getInstance("CNY")));
        payeeFundDetail.setAssetInfo(new BalanceAsset("456", "4561"));
        tradeInfo.setPayeeFundDetail(List.of(payeeFundDetail));
        request.setTradeInfos(List.of(tradeInfo));
        instantPaymentService.pay(request);
    }


}
