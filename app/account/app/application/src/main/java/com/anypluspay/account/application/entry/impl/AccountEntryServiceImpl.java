package com.anypluspay.account.application.entry.impl;

import com.anypluspay.account.application.convertor.AccountingRequestConvertor;
import com.anypluspay.account.application.entry.AccountEntryGroup;
import com.anypluspay.account.application.entry.AccountEntryService;
import com.anypluspay.account.application.entry.EntryContext;
import com.anypluspay.account.application.entry.preprocess.AccountEntryPreprocessor;
import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.BufferedDetail;
import com.anypluspay.account.domain.repository.AccountTransactionRepository;
import com.anypluspay.account.domain.repository.BufferedDetailRepository;
import com.anypluspay.account.domain.service.InnerAccountDomainService;
import com.anypluspay.account.domain.service.OuterAccountDomainService;
import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.facade.dto.AccountingRequest;
import com.anypluspay.account.types.AccountResultCode;
import com.anypluspay.account.types.enums.BufferDetailStatus;
import com.anypluspay.commons.exceptions.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Objects;

/**
 * 入账
 *
 * @author wxj
 * 2023/12/20
 */
@Component
public class AccountEntryServiceImpl implements AccountEntryService {

    @Autowired
    private AccountingRequestConvertor requestConvertor;
    @Autowired
    private List<AccountEntryPreprocessor> accountEntryPreprocessors;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    @Autowired
    private OuterAccountDomainService outerAccountDomainService;

    @Autowired
    private InnerAccountDomainService innerAccountDomainService;

    @Autowired
    private BufferedDetailRepository bufferedDetailRepository;

    @Override
    public void process(AccountingRequest request) {
        EntryContext entryContext = new EntryContext();
        entryContext.setAccountTransaction(requestConvertor.toAccountTransaction(request));
        accountEntryPreprocessors.forEach(accountEntryPreprocessor -> accountEntryPreprocessor.process(request, entryContext));
        transactionTemplate.executeWithoutResult(status -> {
            accountTransactionRepository.store(entryContext.getAccountTransaction());
            processDetail(entryContext.getAccountEntryGroups());
            bufferedDetailRepository.store(entryContext.getBufferedDetails());
        });
    }

    /**
     * 处理缓冲明细
     *
     * @param voucherNo 凭证号
     */
    @Override
    public void processBufferedDetail(String voucherNo) {
        transactionTemplate.executeWithoutResult(status -> {
            BufferedDetail bufferedDetail = bufferedDetailRepository.lock(voucherNo);
            if (bufferedDetail.getStatus() == BufferDetailStatus.INIT) {
                AccountDetail accountDetail = requestConvertor.toAccountDetail(bufferedDetail);
                changeBalance(accountDetail.getAccountNo(), List.of(accountDetail));
                bufferedDetail.setStatus(BufferDetailStatus.SUCCESS);
                bufferedDetailRepository.reStore(bufferedDetail);
            }
        });
    }

    /**
     * 处理账户明细
     *
     * @param accountEntryGroups 账户明细组
     */
    private void processDetail(List<AccountEntryGroup> accountEntryGroups) {
        accountEntryGroups.forEach(accountEntryGroup -> {
            changeBalance(accountEntryGroup.getAccountNo(), accountEntryGroup.getDetails());
        });
    }

    /**
     * 改变余额
     *
     * @param accountNo 账户号
     * @param details   账户明细
     */
    private void changeBalance(String accountNo, List<AccountDetail> details) {
        switch (Objects.requireNonNull(AccountUtil.getAccountFamily(accountNo))) {
            case OUTER:
                outerAccountDomainService.changeBalance(accountNo, details);
                break;
            case INNER:
                innerAccountDomainService.changeBalance(accountNo, details);
                break;
            default:
                throw new BizException(AccountResultCode.ACCOUNT_FAMILY_ILLEGAL);
        }
    }
}