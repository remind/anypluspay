package com.anypluspay.account.facade.manager;

import com.anypluspay.account.facade.manager.request.AccountTitleRequest;
import com.anypluspay.account.types.accounting.AccountTitleScope;
import com.anypluspay.account.types.accounting.AccountTitleType;
import com.anypluspay.component.test.AbstractBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wxj
 * 2025/1/10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountTitleManagerFacadeTest extends AbstractBaseTest {

    @Autowired
    private AccountTitleManagerFacade accountTitleManagerFacade;

    @Test
    public void create() {
        AccountTitleRequest request = new AccountTitleRequest();
        request.setName("test");
        request.setType(AccountTitleType.Assets.getCode());
        request.setScope(AccountTitleScope.INNER_ENTRIES.getCode());
        request.setEnable(true);
        request.setMemo("test");
        String titleCode = accountTitleManagerFacade.create(request);
        Assert.assertNotNull(titleCode);

        request = new AccountTitleRequest();
        request.setParentCode(titleCode);
        request.setName("test");
        request.setMemo("test");
        request.setEnable(true);
        String titleCode1 = accountTitleManagerFacade.create(request);
        Assert.assertNotNull(titleCode1);
    }
}
