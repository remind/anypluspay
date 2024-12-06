package com.anypluspay.account.domain.service;

import com.anypluspay.account.domain.InnerAccount;
import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.InnerAccountDetail;
import com.anypluspay.account.domain.repository.AccountDetailRepository;
import com.anypluspay.account.domain.repository.InnerAccountRepository;
import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.types.AccountResultCode;
import com.anypluspay.account.types.enums.IODirection;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wxj
 * 2023/12/25
 */
@Component
@Slf4j
public class InnerAccountDomainService {

    @Autowired
    private InnerAccountRepository accountRepository;

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    public void changeBalance(String accountNo, List<AccountDetail> details) {
        InnerAccount account;
        try {
            account = accountRepository.lock(accountNo);
        } catch (Exception e) {
            log.error("账户锁定异常,accountNo=" + accountNo, e);
            throw new BizException(AccountResultCode.ACCOUNT_LOCK_TIME_OUT);
        }
        AssertUtil.notNull(account, AccountResultCode.ACCOUNT_ID_NOT_EXISTS);

        details.forEach(accountDetail -> {
            InnerAccountDetail detail = (InnerAccountDetail) accountDetail;
            detail.setIoDirection(AccountUtil.convert(account.getCurrentBalanceDirection()
                    , detail.getCrDr()));
            checkBalance(account, detail);
            updateBalance(account, detail);
        });
        accountDetailRepository.store(details);
        accountRepository.reStore(account);
    }

    private void checkBalance(InnerAccount account, InnerAccountDetail detail) {
        if (detail.getIoDirection() == IODirection.OUT) {
            AssertUtil.isFalse(detail.getAmount().greaterThan(account.getBalance())
                    , AccountResultCode.ACCOUNT_BALANCE_NOT_ENOUGH);
        }
    }

    private void updateBalance(InnerAccount account, InnerAccountDetail detail) {
        detail.setBeforeBalance(account.getBalance());
        account.updateBalance(detail.getIoDirection(), detail.getAmount());
        detail.setAfterBalance(account.getBalance());
    }
}
