package com.anypluspay.account.facade;

import com.anypluspay.account.application.convertor.AccountingRequestConvertor;
import com.anypluspay.account.facade.dto.AccountingRequest;
import com.anypluspay.account.facade.dto.EntryDetail;

import java.util.List;

/**
 * @author wxj
 * 2023/12/20
 */
public class AccountFacadeImpl implements AccountFacade {

    private AccountingRequestConvertor convertor;

    @Override
    public void apply(AccountingRequest accountingRequest) {
        List<EntryDetail> entryDetails = accountingRequest.getEntryDetails();

    }
}
