package com.anypluspay.account.application.account.preprocess;

import com.anypluspay.account.application.account.EntryContext;
import com.anypluspay.account.facade.request.AccountingRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 参数校验
 *
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
