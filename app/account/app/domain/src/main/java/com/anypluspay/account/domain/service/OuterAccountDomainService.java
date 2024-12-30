package com.anypluspay.account.domain.service;

import com.anypluspay.account.domain.OuterAccount;
import com.anypluspay.account.domain.OuterSubAccount;
import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.OuterAccountDetail;
import com.anypluspay.account.domain.repository.AccountDetailRepository;
import com.anypluspay.account.domain.repository.OuterAccountRepository;
import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.types.AccountResultCode;
import com.anypluspay.account.types.enums.DenyStatus;
import com.anypluspay.account.types.enums.IODirection;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.lang.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wxj
 * 2023/12/20
 */
@Component
@Slf4j
public class OuterAccountDomainService {

    @Autowired
    private OuterAccountRepository outerAccountRepository;

    @Autowired
    private AccountDetailRepository accountDetailRepository;

    /**
     * 改变余额
     *
     * @param accountNo
     * @param accountDetails
     */
    public void changeBalance(String accountNo, List<AccountDetail> accountDetails) {
        OuterAccount account;
        try {
            account = outerAccountRepository.lock(accountNo);
        } catch (Exception e) {
            log.error("账户锁定异常,accountNo=" + accountNo, e);
            throw new BizException(AccountResultCode.ACCOUNT_LOCK_TIME_OUT);
        }
        AssertUtil.notNull(account, AccountResultCode.ACCOUNT_ID_NOT_EXISTS);

        accountDetails.forEach(accountDetail -> {
            OuterAccountDetail outerAccountDetail = (OuterAccountDetail) accountDetail;
            outerAccountDetail.setIoDirection(AccountUtil.convert(account.getCurrentBalanceDirection()
                    , outerAccountDetail.getCrDr()));
            checkBalance(account, outerAccountDetail);
            updateBalance(account, outerAccountDetail);
        });

        accountDetailRepository.store(accountDetails);
        outerAccountRepository.reStore(account);
    }

    /**
     * 修改账户止出状态
     *
     * @param outerAccount
     * @param denyStatus
     */
    public void changeDenyStatus(OuterAccount outerAccount, DenyStatus denyStatus) {
        AssertUtil.notNull(outerAccount, AccountResultCode.ACCOUNT_ID_NOT_EXISTS);
        AssertUtil.isFalse(outerAccount.getDenyStatus().equals(denyStatus), "状态已经为：" + outerAccount.getDenyStatus().getDisplayName());
        outerAccount.setDenyStatus(denyStatus);
        outerAccountRepository.reStore(outerAccount);
    }

    private void checkBalance(OuterAccount outerAccount, OuterAccountDetail outerAccountDetail) {
        if (outerAccountDetail.getIoDirection() == IODirection.OUT) {
            AssertUtil.isFalse(outerAccountDetail.getAmount().greaterThan(outerAccount.getAvailableBalance())
                    , AccountResultCode.ACCOUNT_BALANCE_NOT_ENOUGH);
        }
    }

    /**
     * 修改子户余额
     *
     * @param outerAccount
     * @param outerAccountDetail
     */
    private void updateBalance(OuterAccount outerAccount, OuterAccountDetail outerAccountDetail) {
        outerAccountDetail.setBeforeBalance(outerAccount.getBalance());
        outerAccountDetail.getOuterSubAccountDetails().forEach(outerSubAccountDetail -> {
            OuterSubAccount outerSubAccount = outerAccount.getSubAccountByFundType(outerSubAccountDetail.getFundType());
            AssertUtil.notNull(outerSubAccount, AccountResultCode.SUB_ACCOUNT_NOT_EXISTS);

            outerSubAccountDetail.setBeforeBalance(outerSubAccount.getBalance());
            outerSubAccountDetail.setMemo(outerAccountDetail.getMemo());

            outerSubAccount.updateAvailableBalance(outerAccountDetail.getIoDirection(), outerSubAccountDetail.getAmount());
            outerSubAccountDetail.setAfterBalance(outerSubAccount.getBalance());
        });
        outerAccountDetail.setAfterBalance(outerAccount.getBalance());
    }
}
