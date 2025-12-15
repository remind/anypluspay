package com.anypluspay.anypay.domain.account.service;

import com.anypluspay.anypay.domain.account.AccountingEntry;
import com.anypluspay.anypay.domain.account.InnerAccount;
import com.anypluspay.anypay.domain.account.repository.InnerAccountRepository;
import com.anypluspay.anypay.domain.utils.AccountUtil;
import com.anypluspay.anypay.types.account.AccountResultCode;
import com.anypluspay.anypay.types.account.IODirection;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2023/12/25
 */
@Component
@Slf4j
public class InnerAccountDomainService {

    @Autowired
    private InnerAccountRepository accountRepository;

    public void changeBalance(AccountingEntry accountingEntry) {
        InnerAccount account;
        try {
            account = accountRepository.lock(accountingEntry.getAccountNo());
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

        account.updateBalance(ioDirection, accountingEntry.getAmount());
        accountRepository.reStore(account);
    }

}
