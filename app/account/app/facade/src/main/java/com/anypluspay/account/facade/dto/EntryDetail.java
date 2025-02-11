package com.anypluspay.account.facade.dto;

import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.account.types.enums.OperationType;
import com.anypluspay.commons.lang.types.Money;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 入账明细
 * @author wxj
 * 2023/12/20
 */
@Data
public class EntryDetail {

    /**
     * 凭证号，全局唯一
     **/
    @NotNull
    private String voucherNo;

    /**
     * 套号，同一套号内借贷平衡
     */
    private String suiteNo;

    /**
     * 账户号
     **/
    @NotNull
    private String accountNo;

    /**
     * 资金分摊规则
     */
    private List<FundSpiltRule> spiltRules;

    /**
     * 金额
     **/
    @NotNull
    private Money amount;

    /**
     * 操作类型
     */
    @NotNull
    private OperationType operationType;

    /**
     * 借贷标志
     **/
    private CrDr crDr;

    /**
     * 备注
     **/
    private String memo;

}
