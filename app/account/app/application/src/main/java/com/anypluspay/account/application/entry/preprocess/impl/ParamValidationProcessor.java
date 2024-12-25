package com.anypluspay.account.application.entry.preprocess.impl;

import com.anypluspay.account.application.entry.EntryContext;
import com.anypluspay.account.application.entry.preprocess.AccountEntryPreprocessor;
import com.anypluspay.account.facade.dto.AccountingRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 参数校验
 * @author wxj
 * 2023/12/20
 */
@Component
@Order(1)
public class ParamValidationProcessor implements AccountEntryPreprocessor {
    @Override
    public void process(AccountingRequest request, EntryContext entryContext) {

    }
}
