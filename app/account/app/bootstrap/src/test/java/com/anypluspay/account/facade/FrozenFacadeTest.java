package com.anypluspay.account.facade;

import com.anypluspay.account.AccountEntryBaseTest;
import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.OuterAccountDetail;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.account.facade.request.FrozenRequest;
import com.anypluspay.account.facade.request.UnFrozenRequest;
import com.anypluspay.account.types.AccountResultCode;
import com.anypluspay.commons.lang.types.Money;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wxj
 * 2025/1/8
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FrozenFacadeTest extends AccountEntryBaseTest {

    private static final String accountNo = "200100200110000000215600001";

    @Autowired
    private FrozenFacade frozenFacade;

    @Test
    public void frozenSuccess() {
        OuterAccountResponse originAccount = outerAccountManagerFacade.detail(accountNo);
        FrozenRequest request = buildFrozenRequest(new Money("1"));
        frozenFacade.frozen(request);
        OuterAccountResponse newAccount = outerAccountManagerFacade.detail(accountNo);
        assertResult(request.getRequestNo());
        Assert.assertEquals(originAccount.getAvailableBalance().subtract(newAccount.getAvailableBalance()), request.getAmount());
    }

    @Test
    public void frozenBalanceNotEnough() {
        wrapperBizException(AccountResultCode.ACCOUNT_BALANCE_NOT_ENOUGH, () -> {
            OuterAccountResponse originAccount = outerAccountManagerFacade.detail(accountNo);
            FrozenRequest request = buildFrozenRequest(originAccount.getAvailableBalance().add(new Money("1")));
            frozenFacade.frozen(request);
        });
    }

    @Test
    public void unFrozenSuccess() {
        OuterAccountResponse originAccount = outerAccountManagerFacade.detail(accountNo);
        UnFrozenRequest request = buildUnFrozenRequest(new Money("1"));
        frozenFacade.unFrozen(request);
        OuterAccountResponse newAccount = outerAccountManagerFacade.detail(accountNo);
        assertResult(request.getRequestNo());
        Assert.assertEquals(newAccount.getAvailableBalance().subtract(originAccount.getAvailableBalance()), request.getAmount());
    }

    @Test
    public void unFrozenNotEnough() {
        wrapperBizException(AccountResultCode.ACCOUNT_BALANCE_NOT_ENOUGH, () -> {
            OuterAccountResponse originAccount = outerAccountManagerFacade.detail(accountNo);
            UnFrozenRequest request = buildUnFrozenRequest(originAccount.getBalance().subtract(originAccount.getAvailableBalance()).add(new Money("1")));
            frozenFacade.unFrozen(request);
        });
    }

    private FrozenRequest buildFrozenRequest(Money amount) {
        FrozenRequest request = new FrozenRequest();
        request.setRequestNo(randomId());
        request.setAccountNo(accountNo);
        request.setAmount(amount);
        request.setMemo("测试冻结");
        return request;
    }

    private UnFrozenRequest buildUnFrozenRequest(Money amount) {
        UnFrozenRequest request = new UnFrozenRequest();
        request.setRequestNo(randomId());
        request.setAccountNo(accountNo);
        request.setAmount(amount);
        request.setMemo("测试解冻");
        return request;
    }

    private void assertResult(String requestNo) {
        List<AccountDetail> accountDetails = assertRequest(requestNo);
        Assert.assertEquals(1, accountDetails.size());
        accountDetails.forEach(accountDetail -> {
            assetOuterAccountDetail((OuterAccountDetail) accountDetail, requestNo);
        });
    }
}
