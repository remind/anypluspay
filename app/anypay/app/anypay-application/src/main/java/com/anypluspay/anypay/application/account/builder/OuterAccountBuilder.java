package com.anypluspay.anypay.application.account.builder;


import com.anypluspay.anypay.domain.account.AccountTitle;
import com.anypluspay.anypay.domain.account.OuterAccount;
import com.anypluspay.anypay.domain.account.OuterAccountType;
import com.anypluspay.anypay.domain.account.repository.AccountTitleRepository;
import com.anypluspay.anypay.domain.account.repository.AccountTypeRepository;
import com.anypluspay.anypay.application.account.convertor.OuterAccountConvertor;
import com.anypluspay.anypay.application.account.request.OuterAccountRequest;
import com.anypluspay.anypay.types.account.DenyStatus;
import com.anypluspay.anypay.domain.utils.AccountUtil;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.response.GlobalResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

}
