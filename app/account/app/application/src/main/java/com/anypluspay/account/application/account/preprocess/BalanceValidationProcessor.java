package com.anypluspay.account.application.account.preprocess;

import com.anypluspay.account.application.account.EntryContext;
import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.facade.dto.EntryDetail;
import com.anypluspay.account.types.AccountResultCode;
import com.anypluspay.account.types.enums.OperationType;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.response.GlobalResultCode;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平衡校验
 * @author wxj
 * 2023/12/20
 */
@Component
@Order(2)
public class BalanceValidationProcessor implements AccountEntryPreprocessor {
    @Override
    public void process(AccountingRequest request, EntryContext entryContext) {
        Map<String, List<EntryDetail>> entryMap = new HashMap<>();
        AssertUtil.notNull(request.getEntryDetails(), GlobalResultCode.ILLEGAL_PARAM, "入账明细不能为空");
        request.getEntryDetails().forEach(entryDetail -> {
            if (!entryMap.containsKey(entryDetail.getSuiteNo())) {
                entryMap.put(entryDetail.getSuiteNo(), new ArrayList<>());
            }
            entryMap.get(entryDetail.getSuiteNo()).add(entryDetail);
        });

        entryMap.keySet().forEach(suiteNo -> {
            List<EntryDetail> entryDetails = entryMap.get(suiteNo);
            Money drAmount = new Money();
            Money crAmount = new Money();
            entryDetails.forEach(entryDetail -> {
                if (OperationType.NORMAL == entryDetail.getOperationType()) {
                    switch (entryDetail.getCrDr()) {
                        case DEBIT -> drAmount.addTo(entryDetail.getAmount());
                        case CREDIT -> crAmount.addTo(entryDetail.getAmount());
                        default -> throw new BizException(AccountResultCode.CR_DR_NOT_EXISTS);
                    }
                }
            });
            AssertUtil.isTrue(drAmount.equals(crAmount), AccountResultCode.CR_DR_NOT_EXISTS, "套号内借贷不平衡,套号:" + suiteNo);
        });

    }
}
