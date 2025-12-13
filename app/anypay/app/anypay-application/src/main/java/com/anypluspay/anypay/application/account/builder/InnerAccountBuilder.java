package com.anypluspay.anypay.application.account.builder;


import com.anypluspay.anypay.account.AccountTitle;
import com.anypluspay.anypay.account.InnerAccount;
import com.anypluspay.anypay.account.repository.AccountTitleRepository;
import com.anypluspay.anypay.application.account.convertor.InnerAccountConvertor;
import com.anypluspay.anypay.utils.AccountUtil;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.anypay.application.account.request.InnerAccountRequest;
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

    public InnerAccount build(InnerAccountRequest request) {
        InnerAccount account = convertor.toInnerAccount(request);
        account.setBalance(new Money(0, Currency.getInstance(request.getCurrencyCode())));

        AccountTitle accountTitle = accountTitleRepository.load(request.getTitleCode());
        AssertUtil.notNull(accountTitle, "科目不存在");
        AssertUtil.isTrue(accountTitle.isEnable(), "科目状态不是有效");

        account.setBalanceDirection(accountTitle.getBalanceDirection());
        account.setCurrentBalanceDirection(AccountUtil.getBalanceCrdr(accountTitle.getBalanceDirection()));

        return account;
    }
}
