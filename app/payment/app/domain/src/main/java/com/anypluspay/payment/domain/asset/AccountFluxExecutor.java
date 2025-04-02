package com.anypluspay.payment.domain.asset;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import com.anypluspay.account.facade.AccountingFacade;
import com.anypluspay.account.facade.dto.EntryDetail;
import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.account.types.enums.OperationType;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.payment.domain.PaymentConstants;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.FluxOrder;
import com.anypluspay.payment.domain.flux.InstructionType;
import com.anypluspay.payment.types.PayStatus;
import com.anypluspay.payment.types.asset.BalanceAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.anypluspay.payment.domain.asset.AssetFluxExecutor.EXECUTOR_SUFFIX;

/**
 * 账务交换指令执行器
 *
 * @author wxj
 * 2025/2/11
 */
@Component("ACCOUNTING" + EXECUTOR_SUFFIX)
public class AccountFluxExecutor implements AssetFluxExecutor {

    @Autowired
    private AccountingFacade accountingFacade;

    @Override
    public FluxResult increase(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        Assert.isTrue(fluxInstruction.getAssetInfo() instanceof BalanceAsset, "不支持的资产");
        BalanceAsset balanceAsset = (BalanceAsset) fluxInstruction.getAssetInfo();
        String crAccountNo = "";
        String drAccountNo = "";
        if (fluxInstruction.getType() == InstructionType.CLEARING) {
            drAccountNo = balanceAsset.getAccountNo();
            crAccountNo = PaymentConstants.TRANSITION_ACCOUNT;
        } else {
            drAccountNo = PaymentConstants.TRANSITION_ACCOUNT;
            crAccountNo = balanceAsset.getAccountNo();
        }
        AccountingRequest request = buildAccountingRequest(fluxInstruction.getInstructionId(), crAccountNo, drAccountNo, fluxInstruction.getAmount());
        return requestAccounting(request);
    }

    @Override
    public FluxResult decrease(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        Assert.isTrue(fluxInstruction.getAssetInfo() instanceof BalanceAsset, "不支持的资产");
        BalanceAsset balanceAsset = (BalanceAsset) fluxInstruction.getAssetInfo();
        String crAccountNo = "";
        String drAccountNo = "";
        if (fluxInstruction.getType() == InstructionType.CLEARING) {
            drAccountNo = PaymentConstants.TRANSITION_ACCOUNT;
            crAccountNo = balanceAsset.getAccountNo();
        } else {
            drAccountNo = balanceAsset.getAccountNo();
            crAccountNo = PaymentConstants.TRANSITION_ACCOUNT;
        }
        AccountingRequest request = buildAccountingRequest(fluxInstruction.getInstructionId(), crAccountNo, drAccountNo, fluxInstruction.getAmount());
        return requestAccounting(request);
    }

    @Override
    public FluxResult freeze(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        throw new UnsupportedOperationException("不支持的指令");
    }

    @Override
    public FluxResult unfreeze(FluxOrder fluxOrder, FluxInstruction fluxInstruction) {
        throw new UnsupportedOperationException("不支持的指令");
    }

    private FluxResult requestAccounting(AccountingRequest request) {
        FluxResult result = new FluxResult();
        try {
            accountingFacade.apply(request);
            result.setStatus(PayStatus.SUCCESS);
        } catch (BizException e) {
            result.setStatus(PayStatus.FAIL);
            result.setResultCode(e.getCode());
            result.setResultMessage(e.getMessage());
        }
        return result;
    }

    protected AccountingRequest buildAccountingRequest(String requestNo, String crAccountNo, String drAccountNo, Money amount) {
        AccountingRequest request = new AccountingRequest();
        request.setRequestNo(requestNo);
        request.setEntryDetails(buildEntryDetails(crAccountNo, drAccountNo, amount));
        return request;
    }

    protected List<EntryDetail> buildEntryDetails(String crAccountNo, String drAccountNo, Money amount) {
        String suiteNo = "1";
        EntryDetail crEntryDetail = buildEntryDetail(suiteNo, crAccountNo, amount, CrDr.CREDIT);
        EntryDetail drEntryDetail = buildEntryDetail(suiteNo, drAccountNo, amount, CrDr.DEBIT);
        return List.of(crEntryDetail, drEntryDetail);
    }

    protected EntryDetail buildEntryDetail(String suiteNo, String accountNo, Money amount, CrDr crDr) {
        EntryDetail entryDetail = new EntryDetail();
        entryDetail.setVoucherNo(UUID.fastUUID().toString(true));
        entryDetail.setAccountNo(accountNo);
        entryDetail.setSuiteNo(suiteNo);
        entryDetail.setAmount(amount);
        entryDetail.setCrDr(crDr);
        entryDetail.setOperationType(OperationType.NORMAL);
        entryDetail.setMemo("测试入账");
        return entryDetail;
    }
}
