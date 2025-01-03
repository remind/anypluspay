package com.anypluspay.account.domain.accounting.service;

import com.anypluspay.account.domain.AccountDomainConstants;
import com.anypluspay.account.domain.accounting.AccountTitle;
import com.anypluspay.commons.lang.utils.AssertUtil;

/**
 * @author wxj
 * 2023/12/16
 */
public class AccountTitleDomainService {

    /**
     * 设置科目层级
     * @param accountTitle
     * @param parentAccountTitle
     */
    public void setAccountTitleLevel(AccountTitle accountTitle, AccountTitle parentAccountTitle) {
        if (parentAccountTitle == null) {
            accountTitle.setTier(AccountDomainConstants.ACCOUNT_TITLE_TIER_MIN);
            accountTitle.setLeaf(false);
        } else {
            AssertUtil.isTrue(parentAccountTitle.getTier() < AccountDomainConstants.ACCOUNT_TITLE_TIER_MAX
                    , "科目层级已经超过最大值");
            accountTitle.setTier(parentAccountTitle.getTier() + 1);
            accountTitle.setLeaf(accountTitle.getTier() == AccountDomainConstants.ACCOUNT_TITLE_TIER_MAX);
        }
    }
}
