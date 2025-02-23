package com.anypluspay.payment.facade.instant;

import com.anypluspay.account.facade.AccountingFacade;
import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.RefundFacade;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.component.test.AbstractBaseTest;
import com.anypluspay.payment.domain.flux.*;
import com.anypluspay.payment.domain.flux.chain.InstructChain;
import com.anypluspay.payment.domain.payorder.GeneralPayOrder;
import com.anypluspay.payment.domain.payorder.PayOrderStatus;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.domain.repository.GeneralPayOrderRepository;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.request.TradeInfo;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.types.PaymentKey;
import com.anypluspay.payment.types.asset.AssetInfo;
import com.anypluspay.payment.types.asset.AssetTypeCategory;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.asset.BankCardAsset;
import com.anypluspay.payment.types.funds.FundDetail;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author wxj
 * 2025/1/24
 */
public class InstPaymentBaseTest extends AbstractBaseTest {

    protected static final String MERCHANT_ID = "1000000001";
    protected static final String PAYER_MEMBER_ID = "1000000002";
    protected static final String PAYER_ACCOUNT_NO = "200100200110000000215600001";
    protected static final String PAYEE_MEMBER_ID = "1000000003";
    protected static final String PAYEE_ACCOUNT_NO = "200100200110000000315600001";
    protected static final String CHANNEL_CLEARING_ACCOUNT_NO = "40010010011560001";

    @Autowired
    protected GeneralPayOrderRepository generalPayOrderRepository;

    @Autowired
    private FluxOrderRepository fluxOrderRepository;

    @MockitoBean
    private FundInFacade fundInFacade;

    @MockitoBean
    private RefundFacade refundFacade;

    @MockitoBean
    protected AccountingFacade accountingFacade;

    protected void mockFundInSuccess() {
        FundResult fundResult = new FundResult();
        fundResult.setStatus(BizOrderStatus.SUCCESS);
        fundResult.setUnityCode("S001");
        Map<String, Object> extInfo = new HashMap<>();
        extInfo.put(PaymentKey.CLEARING_ACCOUNT_NO, CHANNEL_CLEARING_ACCOUNT_NO);
        fundResult.setExtInfo(extInfo);
        when(fundInFacade.apply(any())).thenReturn(fundResult);
    }

    protected void mockRefundSuccess() {
        FundResult fundResult = new FundResult();
        fundResult.setStatus(BizOrderStatus.SUCCESS);
        fundResult.setUnityCode("S001");
        Map<String, Object> extInfo = new HashMap<>();
        extInfo.put(PaymentKey.CLEARING_ACCOUNT_NO, CHANNEL_CLEARING_ACCOUNT_NO);
        fundResult.setExtInfo(extInfo);
        when(refundFacade.apply(any())).thenReturn(fundResult);
    }

    protected void mockFundInProcessing() {
        FundResult fundResult = new FundResult();
        fundResult.setStatus(BizOrderStatus.PROCESSING);
        fundResult.setUnityCode("P001");
        Map<String, String> responseExtra = new HashMap<>();
        responseExtra.put("instUrl", "channel pay url");
        fundResult.setResponseExtra(responseExtra);
        when(fundInFacade.apply(any())).thenReturn(fundResult);
    }

    protected void mockAccountingSuccess() {
        doNothing().when(accountingFacade).apply(any());
    }

    protected void mockAccountingFail() {
        doThrow(new BizException("accounting fail")).when(accountingFacade).apply(any());
    }

    protected InstantPaymentRequest buildInstantPaymentRequest(double amount) {
        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(randomId());
        request.setMerchantId(MERCHANT_ID);
        request.setPayAmount(new Money(amount, Currency.getInstance("CNY")));
        request.setPayerId(PAYER_MEMBER_ID);
        return request;
    }

    protected TradeInfo buildTradeInfos(double amount, List<FundDetailInfo> payeeFundDetail) {
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setTradeAmount(new Money(amount, Currency.getInstance("CNY")));
        tradeInfo.setTradeId(randomId());
        tradeInfo.setPayeeId(PAYEE_MEMBER_ID);
        tradeInfo.setPayeeFundDetail(payeeFundDetail);
        return tradeInfo;
    }

    protected FundDetailInfo buildBalanceFundDetail(String memberId, String accountNo, double amount) {
        return buildFundDetailInfo(memberId, amount, new BalanceAsset(memberId, accountNo));
    }

    protected FundDetailInfo buildBankCardFundDetail(String memberId, double amount) {
        return buildFundDetailInfo(memberId, amount, new BankCardAsset("bankCardNo456"));
    }

    protected FundDetailInfo buildFundDetailInfo(String memberId, double amount, AssetInfo assetInfo) {
        FundDetailInfo fundDetailInfo = new FundDetailInfo();
        fundDetailInfo.setMemberId(memberId);
        fundDetailInfo.setAmount(new Money(amount, Currency.getInstance("CNY")));
        fundDetailInfo.setAssetInfo(assetInfo);
        return fundDetailInfo;
    }

    protected void assetPayOrder(InstantPaymentRequest request, InstantPaymentResponse response) {
        GeneralPayOrder generalPayOrder = generalPayOrderRepository.load(response.getPayOrderId());
        Assert.assertNotNull(generalPayOrder);
        Assert.assertEquals(request.getRequestId(), generalPayOrder.getRequestId());
        Assert.assertEquals(request.getPayAmount(), generalPayOrder.getAmount());
        Assert.assertEquals(request.getPayerId(), generalPayOrder.getMemberId());
        assetDetail(request.getPayerFundDetail(), generalPayOrder.getPayerDetails());

        List<FundDetailInfo> payeeFundDetailInfos = request.getTradeInfos().stream().map(TradeInfo::getPayeeFundDetail).flatMap(List::stream).toList();
        assetDetail(payeeFundDetailInfos, generalPayOrder.getPayeeDetails());
        assetFlux(response);
    }

    protected void assetDetail(List<FundDetailInfo> fundDetailInfos, List<FundDetail> fundDetails) {
        Assert.assertEquals(fundDetailInfos.size(), fundDetails.size());
        fundDetailInfos.forEach(fundDetailInfo -> {
            FundDetail fundDetail = fundDetails.stream().filter(fundDetail1 ->
                    fundDetail1.getMemberId().equals(fundDetailInfo.getMemberId())
                            && fundDetail1.getAssetInfo().getAssetType().equals(fundDetailInfo.getAssetInfo().getAssetType())
                            && fundDetail1.getAssetInfo().toJsonStr().equals(fundDetailInfo.getAssetInfo().toJsonStr())
            ).findFirst().get();
            Assert.assertNotNull(fundDetail);
            Assert.assertEquals(fundDetailInfo.getAmount(), fundDetail.getAmount());
            Assert.assertEquals(fundDetailInfo.getAssetInfo().getAssetType(), fundDetail.getAssetInfo().getAssetType());
            Assert.assertEquals(fundDetailInfo.getAssetInfo().toJsonStr(), fundDetail.getAssetInfo().toJsonStr());
        });
    }

    protected void assetFlux(InstantPaymentResponse response) {
        FluxOrder fluxOrder = fluxOrderRepository.loadByPayOrderId(response.getPayOrderId());
        Assert.assertNotNull(fluxOrder);
        InstructChain instructChain = fluxOrder.getInstructChain();
        List<FluxInstruction> allFluxInstructions = instructChain.toList();
        List<FluxInstruction> fluxInstructions = allFluxInstructions.stream().filter(f -> f.getStatus() == InstructStatus.SUCCESS
                && f.getType() == InstructionType.PAY
        ).toList();
        fluxInstructions.forEach(fluxInstruction -> {
            if (fluxInstruction.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL) {
                Assert.assertEquals(1,
                        allFluxInstructions.stream().filter(f -> f.getStatus() == InstructStatus.SUCCESS
                                && f.getType() == InstructionType.CLEARING
                                && f.getRelationId().equals(fluxInstruction.getInstructionId())
                                && f.getAmount().equals(fluxInstruction.getAmount())
                        ).count());
            }
        });

        if (response.getOrderStatus() == PayOrderStatus.SUCCESS) {
            Assert.assertEquals(FluxOrderStatus.SUCCESS, fluxOrder.getStatus());
            Assert.assertEquals(0, instructChain.toList().stream().filter(f -> f.getStatus() != InstructStatus.SUCCESS).count());
        } else if (response.getOrderStatus() == PayOrderStatus.FAIL) {
            Assert.assertEquals(FluxOrderStatus.FAIL, fluxOrder.getStatus());
            fluxInstructions.forEach(fluxInstruction -> {
                Assert.assertEquals(1,
                        allFluxInstructions.stream().filter(f -> f.getStatus() == InstructStatus.SUCCESS
                                && f.getType() == InstructionType.REFUND
                                && f.getRelationId().equals(fluxInstruction.getInstructionId())
                                && f.getAmount().equals(fluxInstruction.getAmount())
                        ).count());
            });
        }
    }

}
