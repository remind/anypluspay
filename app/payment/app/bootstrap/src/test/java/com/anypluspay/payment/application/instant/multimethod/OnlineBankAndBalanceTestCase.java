package com.anypluspay.payment.application.instant.multimethod;

import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.application.instant.InstPaymentBaseTest;
import com.anypluspay.payment.application.notify.ExternalFluxNotifyService;
import com.anypluspay.payment.domain.asset.FundDetailSortService;
import com.anypluspay.payment.facade.instant.InstantPaymentFacadeImpl;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.types.asset.AssetTypeCategory;
import com.anypluspay.payment.types.funds.FundDetail;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Currency;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * 网银支付和余额支付的组合支付测试用例
 *
 * @author wxj
 * 2025/4/16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OnlineBankAndBalanceTestCase extends InstPaymentBaseTest {

    @Autowired
    private InstantPaymentFacadeImpl instantPaymentFacade;

    @Autowired
    private ExternalFluxNotifyService externalFluxNotifyService;

    @MockitoBean
    private FundDetailSortService fundDetailSortService;

    private final AtomicReference<FundInRequest> channelRequest = new AtomicReference<>();

    /**
     * 先余额，再网银，都成功
     */
    @Test
    public void testFirstBalanceSuccess() {
        mockFirstBalance();
        mockOnlineBankCreateOrder();

        // 1、网银下单
        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(randomId());
        request.setMerchantId("merchantId123");
        request.setPayAmount(new Money(3000, Currency.getInstance("CNY")));
        request.setPayerId(PAYER_MEMBER_ID);
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, 1000), buildBalanceFundDetail(PAYER_MEMBER_ID, PAYER_ACCOUNT_NO, 2000)));
        request.setPayeeFundDetail(List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, 3000)));
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        Assert.assertEquals(PayProcessStatus.PAYING, response.getOrderStatus());

        // 2、支付成功通知
        makeSuccessNotify();
        assetPayOrder(request, response);
    }

    /**
     * 先余额，再网银，网银失败
     */
    @Test
    public void testFirstBalanceFail() {
        mockFirstBalance();
        mockOnlineBankCreateOrder();

        // 1、网银下单
        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(randomId());
        request.setMerchantId("merchantId123");
        request.setPayAmount(new Money(3000, Currency.getInstance("CNY")));
        request.setPayerId(PAYER_MEMBER_ID);
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, 1000), buildBalanceFundDetail(PAYER_MEMBER_ID, PAYER_ACCOUNT_NO, 2000)));
        request.setPayeeFundDetail(List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, 3000)));
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        Assert.assertEquals(PayProcessStatus.PAYING, response.getOrderStatus());

        // 2、支付失败通知
        makeFailNotify();
        assetPayOrder(request, response);
    }

    /**
     * 先网银，再余额，都成功
     */
    @Test
    public void testFirstOnlineBankSuccess() {
        mockFirstExternal();
        mockOnlineBankCreateOrder();

        // 1、网银下单
        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(randomId());
        request.setMerchantId("merchantId123");
        request.setPayAmount(new Money(3000, Currency.getInstance("CNY")));
        request.setPayerId(PAYER_MEMBER_ID);
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, 1000), buildBalanceFundDetail(PAYER_MEMBER_ID, PAYER_ACCOUNT_NO, 2000)));
        request.setPayeeFundDetail(List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, 3000)));
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        Assert.assertEquals(PayProcessStatus.PAYING, response.getOrderStatus());

        // 2、支付成功通知
        makeSuccessNotify();
        assetPayOrder(request, response);
    }

    /**
     * 先网银，再余额，余额失败
     */
    @Test
    public void testFirstOnlineBankFail() {
        mockFirstExternal();
        mockOnlineBankCreateOrder();

        doAnswer(invocation -> {
            AccountingRequest accountingRequest = invocation.getArgument(0);
            long c = accountingRequest.getEntryDetails().stream().filter(entryDetail -> entryDetail.getAccountNo().equals(PAYER_ACCOUNT_NO) && entryDetail.getCrDr() == CrDr.DEBIT).count();
            if (c == 1) {
                throw new BizException("accounting error");
            }
            return null;
        }).when(accountingFacade).apply(any());

        doAnswer(invocation -> {
            FundResult fundResult = new FundResult();
            fundResult.setStatus(BizOrderStatus.SUCCESS);
            fundResult.setNeedClearing(true);
            fundResult.setClearingAccountNo(CHANNEL_CLEARING_ACCOUNT_NO);
            fundResult.setUnityCode("S001");
            return fundResult;
        }).when(refundFacade).apply(any());

        // 1、网银下单
        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(randomId());
        request.setMerchantId("merchantId123");
        request.setPayAmount(new Money(3000, Currency.getInstance("CNY")));
        request.setPayerId(PAYER_MEMBER_ID);
        request.setPayerFundDetail(List.of(buildBankCardFundDetail(PAYER_MEMBER_ID, 1000), buildBalanceFundDetail(PAYER_MEMBER_ID, PAYER_ACCOUNT_NO, 2000)));
        request.setPayeeFundDetail(List.of(buildBalanceFundDetail(PAYEE_MEMBER_ID, PAYEE_ACCOUNT_NO, 3000)));
        InstantPaymentResponse response = instantPaymentFacade.pay(request);
        Assert.assertEquals(PayProcessStatus.PAYING, response.getOrderStatus());

        // 2、网银支付成功通知
        makeSuccessNotify();
        assetPayOrder(request, response);
    }


    /**
     * 模拟先余额
     */
    private void mockFirstBalance() {
        doAnswer(invocation -> {
            List<FundDetail> fundDetails = invocation.getArgument(0);
            fundDetails.sort((o1, o2) -> {
                if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
                        && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
                ) {
                    return -1;
                } else if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
                        && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
                ) {
                    return 1;
                }
                return 0;
            });
            return null;
        }).when(fundDetailSortService).payerSort(any());

        doAnswer(invocation -> {
            List<FundDetail> fundDetails = invocation.getArgument(0);
            fundDetails.sort((o1, o2) -> {
                if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
                        && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
                ) {
                    return -1;
                } else if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
                        && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
                ) {
                    return 1;
                }
                return 0;
            });
            return null;
        }).when(fundDetailSortService).payeeSort(any());
    }

    private void mockFirstExternal() {
        doAnswer(invocation -> {
            List<FundDetail> fundDetails = invocation.getArgument(0);
            fundDetails.sort((o1, o2) -> {
                if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
                        && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
                ) {
                    return 1;
                } else if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
                        && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
                ) {
                    return -1;
                }
                return 0;
            });
            return null;
        }).when(fundDetailSortService).payerSort(any());

        doAnswer(invocation -> {
            List<FundDetail> fundDetails = invocation.getArgument(0);
            fundDetails.sort((o1, o2) -> {
                if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
                        && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
                ) {
                    return 1;
                } else if (o1.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL
                        && o2.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.ACCOUNTING
                ) {
                    return -1;
                }
                return 0;
            });
            return null;
        }).when(fundDetailSortService).payeeSort(any());
    }

    protected void mockOnlineBankCreateOrder() {
        doAnswer(invocation -> {
            channelRequest.set(invocation.getArgument(0));
            FundResult fundResult = new FundResult();
            fundResult.setStatus(BizOrderStatus.PROCESSING);
            Extension responseExt = new Extension();
            responseExt.add(ChannelExtKey.INST_URL.getCode(), "http://www.testbank.com/pay.do");
            fundResult.setResponseExt(responseExt);
            fundResult.setUnityCode("P001");
            return fundResult;
        }).when(fundInFacade).apply(any());
    }


    private void makeSuccessNotify() {
        FundResult fundResult = new FundResult();
        fundResult.setRequestId(channelRequest.get().getRequestId());
        fundResult.setStatus(BizOrderStatus.SUCCESS);
        fundResult.setClearingAccountNo(CHANNEL_CLEARING_ACCOUNT_NO);
        fundResult.setNeedClearing(true);
        fundResult.setUnityCode("S001");
        externalFluxNotifyService.process(fundResult);
    }

    private void makeFailNotify() {
        FundResult fundResult = new FundResult();
        fundResult.setRequestId(channelRequest.get().getRequestId());
        fundResult.setStatus(BizOrderStatus.FAILED);
        fundResult.setUnityCode("S001");
        externalFluxNotifyService.process(fundResult);
    }

}
