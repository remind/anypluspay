package com.anypluspay.account.application.account.preprocess;

import com.anypluspay.account.application.account.AccountEntryGroup;
import com.anypluspay.account.application.account.EntryContext;
import com.anypluspay.account.application.convertor.AccountingRequestConvertor;
import com.anypluspay.account.domain.AccountDomainConstants;
import com.anypluspay.account.domain.accounting.AccountingEntry;
import com.anypluspay.account.domain.buffer.service.BufferedService;
import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.BufferedDetail;
import com.anypluspay.account.domain.detail.OuterAccountDetail;
import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.facade.dto.EntryDetail;
import com.anypluspay.account.facade.dto.FundSpiltRule;
import com.anypluspay.account.types.enums.BufferDetailStatus;
import com.anypluspay.account.types.enums.OperationType;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分组
 *
 * @author wxj
 * 2023/12/20
 */
@Component
@Order(3)
public class EntryGroupProcessor implements AccountEntryPreprocessor {

    @Autowired
    private AccountingRequestConvertor convertor;

    @Autowired
    private BufferedService bufferedService;

    @Override
    public void process(AccountingRequest request, EntryContext entryContext) {
        Map<String, AccountEntryGroup> groupMap = new HashMap<>();
        request.getEntryDetails().forEach(entryDetail -> {
            AccountDetail accountDetail = convertor.toAccountDetail(request, entryDetail);
            addAccountingEntry(entryContext.getAccountingEntries(), request, entryDetail);
            if (bufferedService.isBuffer(accountDetail)) {
                BufferedDetail bufferedDetail = convertor.toBufferedDetail(request, entryDetail);
                bufferedDetail.setStatus(BufferDetailStatus.INIT);
                entryContext.getBufferedDetails().add(bufferedDetail);
            } else {
                if (!groupMap.containsKey(entryDetail.getAccountNo())) {
                    AccountEntryGroup accountEntryGroup = new AccountEntryGroup();
                    accountEntryGroup.setAccountNo(entryDetail.getAccountNo());
                    accountEntryGroup.setDetails(new ArrayList<>());
                    groupMap.put(entryDetail.getAccountNo(), accountEntryGroup);
                }
                if (accountDetail instanceof OuterAccountDetail) {
                    addOuterSubDetail((OuterAccountDetail) accountDetail, entryDetail.getSpiltRules());
                }
                groupMap.get(entryDetail.getAccountNo()).getDetails().add(accountDetail);
            }
        });
        groupMap.values().forEach(accountEntryGroup -> entryContext.getAccountEntryGroups().add(accountEntryGroup));
    }

    /**
     * 添加外部子明户细
     *
     * @param outerAccountDetail
     * @param fundSpiltRules
     */
    private void addOuterSubDetail(OuterAccountDetail outerAccountDetail, List<FundSpiltRule> fundSpiltRules) {
        if (CollectionUtils.isEmpty(fundSpiltRules)) {
            outerAccountDetail.addSubDetail(AccountDomainConstants.DEFAULT_FUND_TYPE, outerAccountDetail.getAmount());
        } else {
            Money totalAmount = new Money();
            fundSpiltRules.forEach(fundSpiltRule -> {
                totalAmount.addTo(fundSpiltRule.getAmount());
                outerAccountDetail.addSubDetail(fundSpiltRule.getFundType(), fundSpiltRule.getAmount());
            });
            AssertUtil.isTrue(totalAmount.equals(outerAccountDetail.getAmount()), "资金分摊金额不等于总金额");
        }
    }

    /**
     * 添加分录
     *
     * @param accountingEntries
     * @param request
     * @param entryDetail
     */
    private void addAccountingEntry(List<AccountingEntry> accountingEntries, AccountingRequest request, EntryDetail entryDetail) {
        if (entryDetail.getOperationType() == OperationType.NORMAL) {
            AccountingEntry accountingEntry = new AccountingEntry();
            accountingEntry.setAccountingDate(request.getAccountingDate());
            accountingEntry.setAccountNo(entryDetail.getAccountNo());
            accountingEntry.setRequestNo(request.getRequestNo());
            accountingEntry.setVoucherNo(entryDetail.getVoucherNo());
            accountingEntry.setSuiteNo(entryDetail.getSuiteNo());
            accountingEntry.setTitleCode(AccountUtil.getAccountTitle(entryDetail.getAccountNo()));
            accountingEntry.setCrDr(entryDetail.getCrDr());
            accountingEntry.setAmount(entryDetail.getAmount());
            accountingEntry.setMemo(entryDetail.getMemo());
            accountingEntries.add(accountingEntry);
        }
    }
}
