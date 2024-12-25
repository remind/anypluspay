package com.anypluspay.account.application.entry;

import com.anypluspay.account.domain.detail.AccountDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 账户操作分组明细
 * @author wxj
 * 2023/12/20
 */
@Data
public class AccountEntryGroup implements Comparable<AccountEntryGroup> {

    /**
     * 帐号
     */
    private String accountNo;

    /**
     * 账户余额明细
     */
    private List<AccountDetail> details = new ArrayList<>();

    @Override
    public int compareTo(AccountEntryGroup accountEntryGroup) {
        if (accountNo.length() == accountEntryGroup.getAccountNo().length()) {
            return this.accountNo.compareTo(accountEntryGroup.getAccountNo());
        } else if (accountNo.length() > accountEntryGroup.getAccountNo().length()) {
            return 1;
        } else {
            return -1;
        }
    }
}
