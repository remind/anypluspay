package com.anypluspay.account.facade;

import cn.hutool.core.lang.UUID;
import com.anypluspay.account.application.account.AccountOperationService;
import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.facade.dto.EntryDetail;
import com.anypluspay.account.facade.request.TransferRequest;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.account.types.enums.OperationType;
import com.anypluspay.commons.lang.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wxj
 * 2025/1/7
 */
@RestController
@Slf4j
public class TransferFacadeImpl implements TransferFacade {

    @Autowired
    private AccountOperationService accountOperationService;

    @Override
    public void transfer(TransferRequest request) {
        AssertUtil.isTrue(AccountUtil.isOuterAccount(request.getTransferOutAccountNo()) && AccountUtil.isOuterAccount(request.getTransferInAccountNo()),
                "转账账户只能是外部账户");
        AccountingRequest accountingRequest = new AccountingRequest();
        accountingRequest.setRequestNo(request.getRequestNo());
        List<EntryDetail> entryDetails = List.of(buildEntryDetail(request.getTransferOutAccountNo(), CrDr.DEBIT),
                buildEntryDetail(request.getTransferInAccountNo(), CrDr.CREDIT)
        );
        entryDetails.forEach(entryDetail -> {
            entryDetail.setOperationType(OperationType.NORMAL);
            entryDetail.setSuiteNo("1");
            entryDetail.setAmount(request.getAmount());
            entryDetail.setMemo(request.getMemo());
        });
        accountingRequest.setEntryDetails(entryDetails);
        accountOperationService.process(accountingRequest);
    }

    private EntryDetail buildEntryDetail(String accountNo, CrDr crDr) {
        EntryDetail entryDetail = new EntryDetail();
        entryDetail.setVoucherNo(UUID.fastUUID().toString(true));
        entryDetail.setAccountNo(accountNo);
        entryDetail.setCrDr(crDr);
        return entryDetail;
    }
}
