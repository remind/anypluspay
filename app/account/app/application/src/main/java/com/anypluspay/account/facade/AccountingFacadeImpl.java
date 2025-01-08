package com.anypluspay.account.facade;

import com.anypluspay.account.application.account.AccountOperationService;
import com.anypluspay.account.facade.request.AccountingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户服务
 *
 * @author wxj
 * 2023/12/20
 */
@RestController
public class AccountingFacadeImpl implements AccountingFacade {

    @Autowired
    private AccountOperationService accountOperationService;

    @Override
    public void apply(AccountingRequest accountingRequest) {
        accountOperationService.process(accountingRequest);
    }

}
