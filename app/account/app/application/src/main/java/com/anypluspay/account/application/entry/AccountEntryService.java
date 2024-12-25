package com.anypluspay.account.application.entry;

import com.anypluspay.account.facade.dto.AccountingRequest;

/**
 * @author wxj
 * 2023/12/20
 */
public interface AccountEntryService {

    void process(AccountingRequest request);

    void processBufferedDetail(String voucherNo);
}
