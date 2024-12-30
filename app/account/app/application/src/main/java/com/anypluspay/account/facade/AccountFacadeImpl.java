package com.anypluspay.account.facade;

import com.anypluspay.account.application.convertor.AccountingRequestConvertor;
import com.anypluspay.account.domain.OuterAccount;
import com.anypluspay.account.domain.repository.OuterAccountRepository;
import com.anypluspay.account.facade.dto.AccountingRequest;
import com.anypluspay.account.facade.dto.EntryDetail;
import com.anypluspay.account.facade.manager.convertor.OuterAccountConvertor;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账户服务
 * @author wxj
 * 2023/12/20
 */
@RestController
public class AccountFacadeImpl implements AccountFacade {

    private AccountingRequestConvertor convertor;

    @Autowired
    private OuterAccountRepository outerAccountRepository;

    @Autowired
    private OuterAccountConvertor outerAccountConvertor;

    @Override
    public void apply(AccountingRequest accountingRequest) {
        List<EntryDetail> entryDetails = accountingRequest.getEntryDetails();

    }

}
