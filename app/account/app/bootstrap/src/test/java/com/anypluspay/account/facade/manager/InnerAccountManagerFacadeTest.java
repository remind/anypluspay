package com.anypluspay.account.facade.manager;

import com.anypluspay.account.facade.manager.request.InnerAccountRequest;
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
public class InnerAccountManagerFacadeTest extends AbstractBaseTest {

    @Autowired
    private InnerAccountManagerFacade innerAccountManagerFacade;

    @Test
    public void createSuccess() {
        InnerAccountRequest request = new InnerAccountRequest();
        request.setTitleCode("2020001001");
        request.setAccountName("其他应付款-入款-过渡户");
        request.setCurrencyCode("CNY");
        request.setMemo("其他应付款-入款-过渡户");
        innerAccountManagerFacade.create(request);
    }
}
