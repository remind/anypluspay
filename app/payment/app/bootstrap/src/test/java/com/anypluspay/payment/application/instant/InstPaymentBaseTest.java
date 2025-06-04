package com.anypluspay.payment.application.instant;

import com.anypluspay.account.facade.AccountingFacade;
import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.RefundFacade;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.component.test.AbstractBaseTest;
import com.anypluspay.payment.application.instant.common.ModelIntegrityCheck;
import com.anypluspay.payment.domain.flux.*;
import com.anypluspay.payment.domain.flux.chain.FluxChain;
import com.anypluspay.payment.domain.process.PayProcess;
import com.anypluspay.payment.domain.repository.FluxOrderRepository;
import com.anypluspay.payment.domain.repository.PayProcessRepository;
import com.anypluspay.payment.domain.repository.RefundProcessRepository;
import com.anypluspay.payment.facade.request.FundDetailInfo;
import com.anypluspay.payment.facade.request.InstantPaymentRequest;
import com.anypluspay.payment.facade.response.InstantPaymentResponse;
import com.anypluspay.payment.types.PaymentExtKey;
import com.anypluspay.payment.types.PaymentKey;
import com.anypluspay.payment.types.asset.AssetInfo;
import com.anypluspay.payment.types.asset.AssetTypeCategory;
import com.anypluspay.payment.types.asset.BalanceAsset;
import com.anypluspay.payment.types.asset.BankCardAsset;
import com.anypluspay.payment.types.funds.FundDetail;
import com.anypluspay.payment.types.paymethod.PayModel;
import com.anypluspay.payment.types.status.PayProcessStatus;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author wxj
 * 2025/1/24
 */
public class InstPaymentBaseTest extends AbstractBaseTest {

    protected static final String MERCHANT_ID = "100000001";
    protected static final String PAYER_MEMBER_ID = "100000002";
    protected static final String PAYER_ACCOUNT_NO = "200100200110000000215600001";
    protected static final String PAYEE_MEMBER_ID = "100000003";
    protected static final String PAYEE_ACCOUNT_NO = "200100200110000000315600001";
    protected static final String CHANNEL_CLEARING_ACCOUNT_NO = "40010010011560001";

    @Autowired
    protected PayProcessRepository payProcessRepository;

    @Autowired
    protected RefundProcessRepository refundProcessRepository;

    @Autowired
    private FluxOrderRepository fluxOrderRepository;

    @MockitoBean
    protected FundInFacade fundInFacade;

    @MockitoBean
    protected RefundFacade refundFacade;

    @MockitoBean
    protected AccountingFacade accountingFacade;

    @Autowired
    protected ModelIntegrityCheck modelIntegrityCheck;

    protected void mockFundInSuccess() {
        FundResult fundResult = new FundResult();
        fundResult.setStatus(BizOrderStatus.SUCCESS);
        fundResult.setUnityCode("S001");
        fundResult.setNeedClearing(true);
        fundResult.setClearingAccountNo(CHANNEL_CLEARING_ACCOUNT_NO);
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
        Extension responseExt = new Extension();
        responseExt.add(ChannelExtKey.INST_REDIRECTION_DATA.getCode(), "channel pay url");
        fundResult.setResponseExt(responseExt);
        when(fundInFacade.apply(any())).thenReturn(fundResult);
    }

    protected void mockAccountingSuccess() {
        doNothing().when(accountingFacade).apply(any());
    }

    protected InstantPaymentRequest buildInstantPaymentRequest(double amount) {
        InstantPaymentRequest request = new InstantPaymentRequest();
        request.setRequestId(randomId());
        request.setMerchantId(MERCHANT_ID);
        request.setPayAmount(new Money(amount, Currency.getInstance("CNY")));
        request.setPayerId(PAYER_MEMBER_ID);
        return request;
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

    protected void assetPayOrder(InstantPaymentRequest request, InstantPaymentResponse response) {
        modelIntegrityCheck.checkInstantPayment(response.getPaymentId());
        PayProcess generalPayOrder = payProcessRepository.load(response.getPayOrderId());
        Assert.assertNotNull(generalPayOrder);
        Assert.assertEquals(request.getPayAmount(), generalPayOrder.getAmount());
        Assert.assertEquals(request.getPayerId(), generalPayOrder.getMemberId());
        assetDetail(request.getPayerFundDetail(), generalPayOrder.getPayerDetails());

        List<FundDetailInfo> payeeFundDetailInfos = request.getPayeeFundDetail();
        assetDetail(payeeFundDetailInfos, generalPayOrder.getPayeeDetails());
        assetFlux(response);
    }

    protected void assetDetail(List<FundDetailInfo> fundDetailInfos, List<FundDetail> fundDetails) {
        Assert.assertEquals(fundDetailInfos.size(), fundDetails.size());
        fundDetailInfos.forEach(fundDetailInfo -> {
            FundDetail fundDetail = fundDetails.stream().filter(fundDetail1 ->
                    fundDetail1.getMemberId().equals(fundDetailInfo.getMemberId())
                            && fundDetail1.getAssetInfo().getAssetType().getCode().equals(fundDetailInfo.getAssetTypeCode())
                            && fundDetail1.getAssetInfo().toJsonStr().equals(fundDetailInfo.getAssetJsonStr())
            ).findFirst().get();
            Assert.assertNotNull(fundDetail);
            Assert.assertEquals(fundDetailInfo.getAmount(), fundDetail.getAmount());
            Assert.assertEquals(fundDetailInfo.getAssetTypeCode(), fundDetail.getAssetInfo().getAssetType().getCode());
            Assert.assertEquals(fundDetailInfo.getAssetJsonStr(), fundDetail.getAssetInfo().toJsonStr());
        });
    }

    protected void assetFlux(InstantPaymentResponse response) {
        FluxOrder fluxOrder = fluxOrderRepository.loadByPayOrderId(response.getPayOrderId());
        Assert.assertNotNull(fluxOrder);
        FluxChain fluxChain = fluxOrder.getFluxChain();
        List<FluxProcess> allFluxProcesses = fluxChain.toList();
        List<FluxProcess> fluxProcesses = allFluxProcesses.stream().filter(f -> f.getStatus() == FluxProcessStatus.SUCCESS
                && f.getType() == FluxProcessType.NORMAL
        ).toList();
        fluxProcesses.forEach(fluxInstruction -> {
            if (fluxInstruction.getAssetInfo().getAssetType().getAssetTypeCategory() == AssetTypeCategory.EXTERNAL) {
                Assert.assertEquals(1,
                        allFluxProcesses.stream().filter(f -> f.getStatus() == FluxProcessStatus.SUCCESS
                                && f.getType() == FluxProcessType.CLEARING
                                && f.getRelationId().equals(fluxInstruction.getFluxProcessId())
                                && f.getAmount().equals(fluxInstruction.getAmount())
                        ).count());
            }
        });

        if (response.getOrderStatus() == PayProcessStatus.SUCCESS) {
            Assert.assertEquals(FluxOrderStatus.SUCCESS, fluxOrder.getStatus());
            Assert.assertEquals(0, fluxChain.toList().stream().filter(f -> f.getStatus() != FluxProcessStatus.SUCCESS).count());
        } else if (response.getOrderStatus() == PayProcessStatus.FAIL) {
            Assert.assertEquals(FluxOrderStatus.FAIL, fluxOrder.getStatus());
            fluxProcesses.forEach(fluxInstruction -> {
                Assert.assertEquals(1,
                        allFluxProcesses.stream().filter(f -> f.getStatus() == FluxProcessStatus.SUCCESS
                                && f.getDirection() == FluxProcessDirection.REVOKE
                                && f.getRelationId().equals(fluxInstruction.getFluxProcessId())
                                && f.getAmount().equals(fluxInstruction.getAmount())
                        ).count());
            });
        }
    }

}
