package com.anypluspay.admin.account.controller;

import cn.hutool.core.lang.UUID;
import com.anypluspay.account.facade.AccountingFacade;
import com.anypluspay.account.facade.dto.EntryDetail;
import com.anypluspay.account.facade.dto.FundSpiltRule;
import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.account.types.enums.OperationType;
import com.anypluspay.admin.account.request.register.Inner2InnerRegister;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 登账
 *
 * @author wxj
 * 2025/5/9
 */
@RestController
@RequestMapping("/account/accounting/register")
public class AccountingRegisterController {

    @Autowired
    private AccountingFacade accountingFacade;

    /**
     * 内转内
     *
     * @param request
     * @return
     */
    @PostMapping("/inner2inner")
    public ResponseResult<String> inner2InnerRegister(@RequestBody Inner2InnerRegister request) {
        String suiteNo = "1";
        Money amount = new Money(request.getAmount());
        EntryDetail crEntryDetail = buildEntryDetail(suiteNo, request.getCrAccountNo(), amount, CrDr.CREDIT, "", request.getMemo());
        EntryDetail drEntryDetail = buildEntryDetail(suiteNo, request.getDrAccountNo(), amount, CrDr.DEBIT, "", request.getMemo());
        AccountingRequest accountRequest = new AccountingRequest();
        accountRequest.setRequestNo(UUID.fastUUID().toString(true));
        accountRequest.setEntryDetails(List.of(crEntryDetail, drEntryDetail));
        accountingFacade.apply(accountRequest);
        return ResponseResult.success();
    }


    protected EntryDetail buildEntryDetail(String suiteNo, String accountNo, Money amount, CrDr crDr, String fundType, String memo) {
        EntryDetail entryDetail = new EntryDetail();
        entryDetail.setVoucherNo(UUID.fastUUID().toString(true));
        entryDetail.setAccountNo(accountNo);
        entryDetail.setSuiteNo(suiteNo);
        entryDetail.setAmount(amount);
        entryDetail.setCrDr(crDr);
        entryDetail.setOperationType(OperationType.NORMAL);
        if (StringUtils.isNotBlank(fundType)) {
            List<FundSpiltRule> spiltRules = new ArrayList<>();
            FundSpiltRule fundSpiltRule = new FundSpiltRule();
            fundSpiltRule.setAmount(amount);
            fundSpiltRule.setFundType(fundType);
            spiltRules.add(fundSpiltRule);
            entryDetail.setSpiltRules(spiltRules);
        }
        entryDetail.setMemo(memo);
        return entryDetail;
    }
}
