package com.anypluspay.payment.facade;

import com.anypluspay.account.facade.AccountingFacade;
import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.component.test.AbstractBaseTest;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.types.PaymentExtKey;
import com.anypluspay.payment.types.asset.AssetInfo;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.asset.BankCardAsset;
import com.anypluspay.payment.types.paymethod.PayModel;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Currency;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author wxj
 * 2025/5/20
 */
public class BaseMockPaymentTest extends AbstractBaseTest {
    protected static final String MERCHANT_ID = "100000001";
    protected static final String PAYER_MEMBER_ID = "100000002";
    protected static final String PAYER_ACCOUNT_NO = "200100200110000000215600001";
    protected static final String PAYEE_MEMBER_ID = "100000003";
    protected static final String PAYEE_ACCOUNT_NO = "200100200110000000315600001";
    protected static final String CHANNEL_CLEARING_ACCOUNT_NO = "40010010011560001";
    @MockitoBean
    protected FundInFacade fundInFacade;
    @MockitoBean
    protected AccountingFacade accountingFacade;



    protected void mockFundInSuccess() {
        FundResult fundResult = new FundResult();
        fundResult.setStatus(BizOrderStatus.SUCCESS);
        fundResult.setUnityCode("S001");
        fundResult.setNeedClearing(true);
        fundResult.setClearingAccountNo(CHANNEL_CLEARING_ACCOUNT_NO);
        when(fundInFacade.apply(any())).thenReturn(fundResult);
    }



    protected void mockAccountingSuccess() {
        doNothing().when(accountingFacade).apply(any());
    }

    protected FundDetailInfo buildBalanceFundDetail(String memberId, String accountNo, double amount) {
        FundDetailInfo fundDetailInfo = buildFundDetailInfo(memberId, amount, new BalanceAsset(memberId, accountNo));
        fundDetailInfo.setPayModel(PayModel.BALANCE.getCode());
        return fundDetailInfo;
    }

    protected FundDetailInfo buildBankCardFundDetail(String memberId, double amount) {
        FundDetailInfo fundDetailInfo = buildFundDetailInfo(memberId, amount, new BankCardAsset("bankCardNo456"));
        fundDetailInfo.setPayModel(PayModel.ONLINE_BANK.getCode());
        Extension payParam = new Extension();
        payParam.add(PaymentExtKey.PAY_INST.getCode(), "UNLIMITED");
        fundDetailInfo.setPayParam(payParam.toJsonString());
        return fundDetailInfo;
    }

    protected FundDetailInfo buildFundDetailInfo(String memberId, double amount, AssetInfo assetInfo) {
        FundDetailInfo fundDetailInfo = new FundDetailInfo();
        fundDetailInfo.setMemberId(memberId);
        fundDetailInfo.setAmount(new Money(amount, Currency.getInstance("CNY")));
        fundDetailInfo.setAssetTypeCode(assetInfo.getAssetType().getCode());
        fundDetailInfo.setAssetJsonStr(assetInfo.toJsonStr());
        return fundDetailInfo;
    }

}
