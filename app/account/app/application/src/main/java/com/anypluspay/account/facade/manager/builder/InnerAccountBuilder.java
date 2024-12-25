package com.anypluspay.account.facade.manager.builder;


import com.anypluspay.account.domain.InnerAccount;
import com.anypluspay.account.domain.accounting.AccountTitle;
import com.anypluspay.account.domain.repository.AccountTitleRepository;
import com.anypluspay.account.domain.utils.AccountUtil;
import com.anypluspay.account.facade.manager.convertor.InnerAccountConvertor;
import com.anypluspay.account.facade.manager.dto.InnerAccountAddRequest;
import com.anypluspay.account.types.accounting.AccountTitleStatus;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Currency;

/**
 * @author wxj
 * 2023/12/23
 */
@Component
public class InnerAccountBuilder {

    @Autowired
    private InnerAccountConvertor convertor;

    @Autowired
    private AccountTitleRepository accountTitleRepository;

    public InnerAccount build(InnerAccountAddRequest request) {
        InnerAccount account = convertor.toOuterAccount(request);
        account.setBalance(new Money(0, Currency.getInstance(request.getCurrencyCode())));

        AccountTitle accountTitle = accountTitleRepository.load(request.getTitleCode());
        AssertUtil.notNull(accountTitle, "科目不存在");
        AssertUtil.isTrue(accountTitle.getStatus() == AccountTitleStatus.VALID, "科目状态不是有效");

        account.setBalanceDirection(accountTitle.getBalanceDirection());
        account.setCurrentBalanceDirection(AccountUtil.getBalanceCrdr(accountTitle.getBalanceDirection()));

        return account;
    }
}
