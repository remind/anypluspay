package com.anypluspay.account.application.account;

import com.anypluspay.account.domain.AccountTransaction;
import com.anypluspay.account.domain.accounting.AccountingEntry;
import com.anypluspay.account.domain.detail.BufferedDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 入账上下文
 * @author wxj
 * 2023/12/20
 */
@Data
public class EntryContext {

    /**
     * 入账事务
     */
    private AccountTransaction accountTransaction;

    /**
     * 分组操作明细
     */
    private List<AccountEntryGroup> accountEntryGroups = new ArrayList<>();

    /**
     * 缓冲入账明细
     */
    private List<BufferedDetail> bufferedDetails = new ArrayList<>();

    /**
     * 分录明细
     */
    private List<AccountingEntry> accountingEntries = new ArrayList<>();
}
