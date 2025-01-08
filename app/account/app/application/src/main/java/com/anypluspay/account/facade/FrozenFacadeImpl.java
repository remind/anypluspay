package com.anypluspay.account.facade;

import cn.hutool.core.lang.UUID;
import com.anypluspay.account.application.account.AccountOperationService;
import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.facade.dto.EntryDetail;
import com.anypluspay.account.facade.request.FrozenRequest;
import com.anypluspay.account.facade.request.UnFrozenRequest;
import com.anypluspay.account.types.enums.OperationType;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 冻结解冻
 *
 * @author wxj
 * 2025/1/8
 */
@RestController
public class FrozenFacadeImpl implements FrozenFacade {

    @Autowired
    private AccountOperationService accountOperationService;

    @Override
    public void frozen(FrozenRequest request) {
        AssertUtil.isTrue(AccountUtil.isOuterAccount(request.getAccountNo()), "冻结只能是外部账户");
        AccountingRequest accountingRequest = new AccountingRequest();
        accountingRequest.setRequestNo(request.getRequestNo());
        EntryDetail entryDetail = buildEntryDetail(OperationType.FROZEN, request.getAccountNo(), request.getAmount(), request.getMemo());
        accountingRequest.setEntryDetails(List.of(entryDetail));
        accountOperationService.process(accountingRequest);
    }

    @Override
    public void unFrozen(UnFrozenRequest request) {
        AssertUtil.isTrue(AccountUtil.isOuterAccount(request.getAccountNo()), "解冻只能是外部账户");
        AccountingRequest accountingRequest = new AccountingRequest();
        accountingRequest.setRequestNo(request.getRequestNo());
        EntryDetail entryDetail = buildEntryDetail(OperationType.UNFROZEN, request.getAccountNo(), request.getAmount(), request.getMemo());
        accountingRequest.setEntryDetails(List.of(entryDetail));
        accountOperationService.process(accountingRequest);
    }

    private EntryDetail buildEntryDetail(OperationType operationType, String accountNo, Money amount, String memo) {
        EntryDetail entryDetail = new EntryDetail();
        entryDetail.setOperationType(operationType);
        entryDetail.setVoucherNo(UUID.fastUUID().toString(true));
        entryDetail.setAccountNo(accountNo);
        entryDetail.setAmount(amount);
        entryDetail.setMemo(memo);
        return entryDetail;
    }
}
