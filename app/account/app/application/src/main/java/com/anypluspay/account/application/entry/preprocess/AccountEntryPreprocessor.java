package com.anypluspay.account.application.entry.preprocess;

import com.anypluspay.account.application.entry.EntryContext;
import com.anypluspay.account.facade.dto.AccountingRequest;

/**
 * @author wxj
 * 2023/12/20
 */
public interface AccountEntryPreprocessor {

    void process(AccountingRequest request, EntryContext entryContext);
}
