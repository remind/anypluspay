package com.anypluspay.anypay.application.account.builder;

import com.anypluspay.anypay.account.AccountDomainConstants;
import com.anypluspay.anypay.account.AccountTitle;
import com.anypluspay.anypay.account.repository.AccountTitleRepository;
import com.anypluspay.anypay.types.account.AccountTitleScope;
import com.anypluspay.anypay.types.account.AccountTitleType;
import com.anypluspay.anypay.types.account.BalanceDirection;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.anypay.application.account.request.AccountTitleRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2024/12/28
 */
@Component
public class AccountTitleBuilder {

    @Autowired
    private AccountTitleRepository accountTitleRepository;

    public AccountTitle build(AccountTitleRequest request, AccountTitle parentAccountTitle) {
        AccountTitle accountTitle = new AccountTitle();
        accountTitle.setName(request.getName());
        accountTitle.setParentCode(request.getParentCode());
        accountTitle.setEnable(request.getEnable());
        accountTitle.setMemo(request.getMemo());
        if (parentAccountTitle == null) {
            accountTitle.setType(EnumUtil.getByCode(AccountTitleType.class, request.getType()));
            accountTitle.setBalanceDirection(getBalanceDirection(accountTitle.getType()));
            accountTitle.setScope(EnumUtil.getByCode(AccountTitleScope.class, request.getScope()));
            accountTitle.setTier(AccountDomainConstants.ACCOUNT_TITLE_TIER_MIN);
            accountTitle.setLeaf(false);
        } else {
            if (parentAccountTitle.getTier() == AccountDomainConstants.ACCOUNT_TITLE_TIER_MAX) {
                throw new BizException("科目层级已经超过最大值：" + AccountDomainConstants.ACCOUNT_TITLE_TIER_MAX);
            }
            accountTitle.setType(parentAccountTitle.getType());
            accountTitle.setBalanceDirection(parentAccountTitle.getBalanceDirection());
            accountTitle.setScope(parentAccountTitle.getScope());
            accountTitle.setTier(parentAccountTitle.getTier() + 1);
            accountTitle.setLeaf(accountTitle.getTier() == AccountDomainConstants.ACCOUNT_TITLE_TIER_MAX);
        }
        accountTitle.setCode(accountTitleRepository.getTitleCode(accountTitle.getType(), accountTitle.getTier(), request.getParentCode()));
        return accountTitle;
    }


    public AccountTitle validate(AccountTitleRequest request) {
        AccountTitle parentAccountTitle = null;
        if (StringUtils.isBlank(request.getParentCode())) {
            // 一级科目

        } else {
            // 二、三级科目
            parentAccountTitle = accountTitleRepository.load(request.getParentCode());
            AssertUtil.notNull(parentAccountTitle, "父科目不存在");
        }

        return parentAccountTitle;
    }

    private BalanceDirection getBalanceDirection(AccountTitleType type) {
        return switch (type) {
            case Assets -> BalanceDirection.DEBIT;
            case Liabilities, OwnersEquity, IncreaseAndDecrease, Public -> BalanceDirection.CREDIT;
        };
    }

}
