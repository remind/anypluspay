package com.anypluspay.account;

import com.anypluspay.account.application.account.AccountOperationService;
import com.anypluspay.account.domain.AccountTransaction;
import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.InnerAccountDetail;
import com.anypluspay.account.domain.detail.OuterAccountDetail;
import com.anypluspay.account.domain.repository.AccountDetailRepository;
import com.anypluspay.account.domain.repository.AccountTransactionRepository;
import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.facade.dto.EntryDetail;
import com.anypluspay.account.facade.manager.InnerAccountManagerFacade;
import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.account.facade.manager.response.InnerAccountResponse;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.account.types.enums.IODirection;
import com.anypluspay.account.types.enums.OperationType;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.component.test.AbstractBaseTest;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 入账测试基类
 *
 * @author wxj
 * 2025/1/3
 */
public abstract class AccountEntryBaseTest extends AbstractBaseTest {

    protected  final String DEFAULT_SUITE_NO = "1";

    @Autowired
    protected AccountOperationService accountOperationService;

    @Autowired
    protected AccountTransactionRepository accountTransactionRepository;

    @Autowired
    protected AccountDetailRepository accountDetailRepository;

    @Autowired
    protected InnerAccountManagerFacade innerAccountManagerFacade;

    @Autowired
    protected OuterAccountManagerFacade outerAccountManagerFacade;

    protected List<AccountDetail> assertRequest(String requestNo) {
        AccountTransaction accountTransaction = accountTransactionRepository.loadByRequestNo(requestNo);
        Assert.assertNotNull(accountTransaction);
        Assert.assertEquals(AccountUtil.getTodayAccounting(), accountTransaction.getAccountingDate());
        List<AccountDetail> accountDetails = accountDetailRepository.loadByRequestNo(requestNo);
        Assert.assertNotNull(accountDetails);
        return accountDetails;
    }

    protected void assertAccountResult(AccountingRequest request) {
        List<AccountDetail> accountDetails = assertRequest(request.getRequestNo());
        Assert.assertEquals(request.getEntryDetails().size(), accountDetails.size());
        request.getEntryDetails().forEach(entryDetail -> {
            List<AccountDetail> entryAccountDetails = accountDetails.stream().filter(accountDetail -> accountDetail.getVoucherNo().equals(entryDetail.getVoucherNo())).toList();
            Assert.assertEquals(1, entryAccountDetails.size());
            assertAccountDetail(entryDetail, entryAccountDetails.get(0));
        });
    }

    protected void assertAccountDetail(EntryDetail entryDetail, AccountDetail accountDetail) {
        Assert.assertEquals(entryDetail.getVoucherNo(), accountDetail.getVoucherNo());
        Assert.assertEquals(entryDetail.getAccountNo(), accountDetail.getAccountNo());
        Assert.assertEquals(entryDetail.getMemo(), accountDetail.getMemo());
        Assert.assertEquals(entryDetail.getAmount(), accountDetail.getAmount());
        Assert.assertEquals(entryDetail.getCrDr(), accountDetail.getCrDr());
        if (accountDetail instanceof OuterAccountDetail outerAccountDetail) {
            assetOuterAccountDetail(outerAccountDetail, accountDetail.getRequestNo());
        } else if (accountDetail instanceof InnerAccountDetail innerAccountDetail) {
            if (innerAccountDetail.getIoDirection() == IODirection.IN) {
                Assert.assertEquals(innerAccountDetail.getAfterBalance(), innerAccountDetail.getBeforeBalance().add(innerAccountDetail.getAmount()));
            } else if (innerAccountDetail.getIoDirection() == IODirection.OUT) {
                Assert.assertEquals(innerAccountDetail.getAfterBalance(), innerAccountDetail.getBeforeBalance().subtract(innerAccountDetail.getAmount()));
            } else {
                Assert.fail();
            }
            InnerAccountResponse innerAccount = innerAccountManagerFacade.detail(innerAccountDetail.getAccountNo());
            Assert.assertEquals(innerAccountDetail.getAfterBalance(), innerAccount.getBalance());
        }
    }

    protected void assetOuterAccountDetail(OuterAccountDetail outerAccountDetail, String requestNo) {
        Assert.assertNotNull(outerAccountDetail.getOperationType());
        if (outerAccountDetail.getOperationType() == OperationType.NORMAL) {
            if (outerAccountDetail.getIoDirection() == IODirection.IN) {
                Assert.assertEquals(outerAccountDetail.getAfterBalance(), outerAccountDetail.getBeforeBalance().add(outerAccountDetail.getAmount()));
            } else if (outerAccountDetail.getIoDirection() == IODirection.OUT) {
                Assert.assertEquals(outerAccountDetail.getAfterBalance(), outerAccountDetail.getBeforeBalance().subtract(outerAccountDetail.getAmount()));
            } else {
                Assert.fail();
            }
        } else if (outerAccountDetail.getOperationType() == OperationType.FROZEN) {
            Assert.assertEquals(outerAccountDetail.getAfterBalance(), outerAccountDetail.getBeforeBalance());
        }

        Money subTotal = new Money();
        outerAccountDetail.getOuterSubAccountDetails().forEach(outerSubAccountDetail -> {
            subTotal.addTo(outerSubAccountDetail.getAmount());
            Assert.assertNotNull(outerSubAccountDetail.getFundType());
            Assert.assertEquals(outerAccountDetail.getVoucherNo(), outerSubAccountDetail.getVoucherNo());
            Assert.assertEquals(requestNo, outerSubAccountDetail.getRequestNo());
            Assert.assertEquals(outerAccountDetail.getAccountNo(), outerSubAccountDetail.getAccountNo());
        });
        Assert.assertEquals(subTotal, outerAccountDetail.getAmount());
        OuterAccountResponse outerAccount = outerAccountManagerFacade.detail(outerAccountDetail.getAccountNo());
        Assert.assertEquals(outerAccountDetail.getAfterBalance(), outerAccount.getBalance());
    }

    protected AccountingRequest buildAccountingRequest(String crAccountNo, String drAccountNo, Money amount) {
        AccountingRequest request = new AccountingRequest();
        request.setRequestNo(randomId());
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
        entryDetail.setVoucherNo(randomId());
        entryDetail.setAccountNo(accountNo);
        entryDetail.setSuiteNo(suiteNo);
        entryDetail.setAmount(amount);
        entryDetail.setCrDr(crDr);
        entryDetail.setOperationType(OperationType.NORMAL);
        entryDetail.setMemo("测试入账");
        return entryDetail;
    }
}
