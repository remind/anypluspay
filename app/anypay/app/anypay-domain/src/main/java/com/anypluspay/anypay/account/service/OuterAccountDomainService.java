package com.anypluspay.anypay.account.service;


import com.anypluspay.anypay.account.AccountingEntry;
import com.anypluspay.anypay.account.OuterAccount;
import com.anypluspay.anypay.account.repository.OuterAccountRepository;
import com.anypluspay.anypay.types.account.AccountResultCode;
import com.anypluspay.anypay.types.account.DenyStatus;
import com.anypluspay.anypay.types.account.IODirection;
import com.anypluspay.anypay.utils.AccountUtil;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2023/12/20
 */
@Component
@Slf4j
public class OuterAccountDomainService {

    @Autowired
    private OuterAccountRepository outerAccountRepository;

    /**
     * 改变余额
     *
     * @param accountingEntry
     */
    public void changeBalance(AccountingEntry accountingEntry) {
        OuterAccount account;
        try {
            account = outerAccountRepository.lock(accountingEntry.getAccountNo());
        } catch (Exception e) {
            log.error("账户锁定异常,accountNo=" + accountingEntry.getAccountNo(), e);
            throw new BizException(AccountResultCode.ACCOUNT_LOCK_TIME_OUT);
        }
        AssertUtil.notNull(account, AccountResultCode.ACCOUNT_NOT_EXISTS);

        IODirection ioDirection = AccountUtil.convert(account.getCurrentBalanceDirection(), accountingEntry.getCrDr());
        if (ioDirection == IODirection.OUT) {
            AssertUtil.isFalse(accountingEntry.getAmount().greaterThan(account.getBalance())
                    , AccountResultCode.ACCOUNT_BALANCE_NOT_ENOUGH);
        }
        validateAccount(account, ioDirection);

        account.updateBalance(ioDirection, accountingEntry.getAmount());
        outerAccountRepository.reStore(account);
    }

    /**
     * 修改账户止出状态
     *
     * @param outerAccount
     * @param denyStatus
     */
    public void changeDenyStatus(OuterAccount outerAccount, DenyStatus denyStatus) {
        AssertUtil.notNull(outerAccount, AccountResultCode.ACCOUNT_NOT_EXISTS);
        AssertUtil.isFalse(outerAccount.getDenyStatus().equals(denyStatus), "状态已经为：" + outerAccount.getDenyStatus().getDisplayName());
        outerAccount.setDenyStatus(denyStatus);
        outerAccountRepository.reStore(outerAccount);
    }

    /**
     * 验证账户状态
     *
     * @param outerAccount
     * @param ioDirection
     */
    private void validateAccount(OuterAccount outerAccount, IODirection ioDirection) {
        AssertUtil.isFalse(outerAccount.getDenyStatus() == DenyStatus.DENY_IN_OUT, AccountResultCode.ACCOUNT_DENY_IN_OUT);
        AssertUtil.isFalse(outerAccount.getDenyStatus() == DenyStatus.DENY_OUT && ioDirection == IODirection.OUT, AccountResultCode.ACCOUNT_DENY_OUT);
        AssertUtil.isFalse(outerAccount.getDenyStatus() == DenyStatus.DENY_IN && ioDirection == IODirection.IN, AccountResultCode.ACCOUNT_DENY_IN);
    }
}
