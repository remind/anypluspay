package com.anypluspay.account.facade.manager.builder;


import com.anypluspay.account.domain.AccountDomainConstants;
import com.anypluspay.account.domain.OuterAccount;
import com.anypluspay.account.domain.OuterAccountType;
import com.anypluspay.account.domain.accounting.AccountTitle;
import com.anypluspay.account.domain.repository.AccountTitleRepository;
import com.anypluspay.account.domain.repository.AccountTypeRepository;
import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.facade.manager.convertor.OuterAccountConvertor;
import com.anypluspay.account.facade.manager.request.OuterAccountRequest;
import com.anypluspay.account.types.enums.DenyStatus;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.response.GlobalResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2023/12/22
 */
@Component
public class OuterAccountBuilder {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private AccountTitleRepository accountTitleRepository;

    @Autowired
    private OuterAccountConvertor outerAccountConvertor;

    public OuterAccount build(OuterAccountRequest request) {
        OuterAccountType outerAccountType = accountTypeRepository.load(request.getAccountType());
        AssertUtil.notNull(outerAccountType, GlobalResultCode.ILLEGAL_PARAM, "账户类型不存在");
        OuterAccount outerAccount = outerAccountConvertor.toOuterAccount(request, outerAccountType);
        if (StringUtils.isBlank(outerAccount.getAccountName())) {
            outerAccount.setAccountName(outerAccountType.getName());
        }
        outerAccount.setDenyStatus(DenyStatus.INIT);
        fillBalanceDirection(outerAccount, outerAccountType.getTitleCode());
        fillOuterSubAccount(outerAccount, outerAccountType.getFundTypes());
        return outerAccount;
    }

    public List<OuterAccount> build(List<OuterAccountRequest> requests) {
        List<OuterAccount> outerAccounts = new ArrayList<>();
        requests.forEach(outerAccountAddRequest -> outerAccounts.add(build(outerAccountAddRequest)));
        return outerAccounts;
    }

    private void fillBalanceDirection(OuterAccount outerAccount, String titleCode) {
        AccountTitle accountTitle = accountTitleRepository.load(titleCode);
        outerAccount.setBalanceDirection(accountTitle.getBalanceDirection());
        outerAccount.setCurrentBalanceDirection(AccountUtil.getBalanceCrdr(accountTitle.getBalanceDirection()));
    }

    private void fillOuterSubAccount(OuterAccount outerAccount, List<String> fundTypes) {
        if (CollectionUtils.isEmpty(fundTypes)) {
            outerAccount.addSubAccount(AccountDomainConstants.DEFAULT_FUND_TYPE);
        } else {
            fundTypes.forEach(outerAccount::addSubAccount);
        }
    }

}
