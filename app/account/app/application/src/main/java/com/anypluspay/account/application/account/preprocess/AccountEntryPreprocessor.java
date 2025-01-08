package com.anypluspay.account.application.account.preprocess;

import com.anypluspay.account.application.account.EntryContext;
import com.anypluspay.account.facade.request.AccountingRequest;

/**
 * @author wxj
 * 2023/12/20
 */
public interface AccountEntryPreprocessor {

    void process(AccountingRequest request, EntryContext entryContext);
}
