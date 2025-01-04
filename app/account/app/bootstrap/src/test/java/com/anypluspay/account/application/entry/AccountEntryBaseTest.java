package com.anypluspay.account.application.entry;

import com.anypluspay.account.BaseTest;
import com.anypluspay.account.domain.AccountTransaction;
import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.InnerAccountDetail;
import com.anypluspay.account.domain.detail.OuterAccountDetail;
import com.anypluspay.account.domain.repository.AccountDetailRepository;
import com.anypluspay.account.domain.repository.AccountTransactionRepository;
import com.anypluspay.account.facade.dto.AccountingRequest;
import com.anypluspay.account.facade.dto.EntryDetail;
import com.anypluspay.account.facade.manager.AccountManagerFacade;
import com.anypluspay.account.facade.manager.response.InnerAccountResponse;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.account.types.enums.IODirection;
import com.anypluspay.commons.lang.types.Money;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 入账测试基类
 *
 * @author wxj
 * 2025/1/3
 */
public abstract class AccountEntryBaseTest extends BaseTest {

    @Autowired
    protected AccountEntryService accountEntryService;

    @Autowired
    protected AccountTransactionRepository accountTransactionRepository;

    @Autowired
    protected AccountDetailRepository accountDetailRepository;

    @Autowired
    private AccountManagerFacade accountManagerFacade;

    protected void assertResult(AccountingRequest request) {
        AccountTransaction accountTransaction = accountTransactionRepository.loadByRequestNo(request.getRequestNo());
        Assert.assertNotNull(accountTransaction);
        Assert.assertEquals(request.getAccountingDate(), accountTransaction.getAccountingDate());
        List<AccountDetail> accountDetails = accountDetailRepository.loadByRequestNo(request.getRequestNo());
        Assert.assertNotNull(accountDetails);
        Assert.assertEquals(request.getEntryDetails().size(), accountDetails.size());

        request.getEntryDetails().forEach(entryDetail -> {
            List<AccountDetail> entryAccountDetails = accountDetails.stream().filter(accountDetail -> accountDetail.getVoucherNo().equals(entryDetail.getVoucherNo())).toList();
            Assert.assertEquals(1, entryAccountDetails.size());
            assertDetail(entryDetail, entryAccountDetails.get(0));
        });
    }

    private void assertDetail(EntryDetail entryDetail, AccountDetail accountDetail) {
        Assert.assertEquals(entryDetail.getVoucherNo(), accountDetail.getVoucherNo());
        Assert.assertEquals(entryDetail.getAccountNo(), accountDetail.getAccountNo());
        Assert.assertEquals(entryDetail.getMemo(), accountDetail.getMemo());
        Assert.assertEquals(entryDetail.getAmount(), accountDetail.getAmount());
        Assert.assertEquals(entryDetail.getCrDr(), accountDetail.getCrDr());
        if (accountDetail instanceof OuterAccountDetail outerAccountDetail) {
            if (outerAccountDetail.getIoDirection() == IODirection.IN) {
                Assert.assertEquals(outerAccountDetail.getAfterBalance(), outerAccountDetail.getBeforeBalance().add(outerAccountDetail.getAmount()));
            } else if (outerAccountDetail.getIoDirection() == IODirection.OUT) {
                Assert.assertEquals(outerAccountDetail.getAfterBalance(), outerAccountDetail.getBeforeBalance().subtract(outerAccountDetail.getAmount()));
            } else {
                Assert.fail();
            }

            Money subTotal = new Money();
            outerAccountDetail.getOuterSubAccountDetails().forEach(outerSubAccountDetail -> {
                subTotal.addTo(outerSubAccountDetail.getAmount());
                Assert.assertNotNull(outerSubAccountDetail.getFundType());
                Assert.assertEquals(entryDetail.getVoucherNo(), outerSubAccountDetail.getVoucherNo());
                Assert.assertEquals(accountDetail.getRequestNo(), outerSubAccountDetail.getRequestNo());
                Assert.assertEquals(entryDetail.getAccountNo(), outerSubAccountDetail.getAccountNo());
            });
            Assert.assertEquals(subTotal, outerAccountDetail.getAmount());
            OuterAccountResponse outerAccount = accountManagerFacade.queryOuterAccount(outerAccountDetail.getAccountNo());
            Assert.assertEquals(outerAccountDetail.getAfterBalance(), outerAccount.getBalance());
        } else if (accountDetail instanceof InnerAccountDetail innerAccountDetail) {
            if (innerAccountDetail.getIoDirection() == IODirection.IN) {
                Assert.assertEquals(innerAccountDetail.getAfterBalance(), innerAccountDetail.getBeforeBalance().add(innerAccountDetail.getAmount()));
            } else if (innerAccountDetail.getIoDirection() == IODirection.OUT) {
                Assert.assertEquals(innerAccountDetail.getAfterBalance(), innerAccountDetail.getBeforeBalance().subtract(innerAccountDetail.getAmount()));
            } else {
                Assert.fail();
            }
            InnerAccountResponse innerAccount = accountManagerFacade.queryInnerAccount(innerAccountDetail.getAccountNo());
            Assert.assertEquals(innerAccountDetail.getAfterBalance(), innerAccount.getBalance());
        }
    }

    protected AccountingRequest buildAccountingRequest(String crAccountNo, String drAccountNo, Money amount) {
        AccountingRequest request = new AccountingRequest();
        request.setAccountingDate("20231221");
        request.setRequestNo(getUUID());
        request.setEntryDetails(buildEntryDetails(crAccountNo, drAccountNo, amount));
        return request;
    }

    protected List<EntryDetail> buildEntryDetails(String crAccountNo, String drAccountNo, Money amount) {
        String suiteNo = "1";
        String memo = "测试入账";
        EntryDetail crEntryDetail = buildEntryDetail(suiteNo, crAccountNo, amount, CrDr.CREDIT, memo);
        EntryDetail drEntryDetail = buildEntryDetail(suiteNo, drAccountNo, amount, CrDr.DEBIT, memo);
        return List.of(crEntryDetail, drEntryDetail);
    }

    protected EntryDetail buildEntryDetail(String suiteNo, String accountNo, Money amount, CrDr crDr, String memo) {
        EntryDetail entryDetail = new EntryDetail();
        entryDetail.setVoucherNo(getUUID());
        entryDetail.setAccountNo(accountNo);
        entryDetail.setSuiteNo(suiteNo);
        entryDetail.setAmount(amount);
        entryDetail.setCrDr(crDr);
        entryDetail.setMemo(memo);
        return entryDetail;
    }
}
