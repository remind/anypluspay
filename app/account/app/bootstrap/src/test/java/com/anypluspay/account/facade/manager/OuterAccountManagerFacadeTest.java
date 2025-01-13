package com.anypluspay.account.facade.manager;

import com.anypluspay.account.facade.manager.request.OuterAccountRequest;
import com.anypluspay.component.test.AbstractBaseTest;
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
public class OuterAccountManagerFacadeTest extends AbstractBaseTest {

    @Autowired
    private OuterAccountManagerFacade outerAccountManagerFacade;

    @Test
    public void createSuccess() {
        OuterAccountRequest request = new OuterAccountRequest();
        request.setMemberId("100000005");
        request.setAccountType("101");
        request.setAccountName("基本户");
        outerAccountManagerFacade.create(request);
    }
}
