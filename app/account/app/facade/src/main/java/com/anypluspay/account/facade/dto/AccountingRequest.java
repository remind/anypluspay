package com.anypluspay.account.facade.dto;

import lombok.Data;

import java.util.List;

/**
 * 入账请求
 * @author wxj
 * 2023/12/20
 */
@Data
public class AccountingRequest {

    /**
     * 入账请求流水号，唯一，一般由支付按统一规则生成
     */
    private String requestNo;

    /**
     * 会计日
     **/
    private String accountingDate;

    /**
     * 明细明细
     */
    private List<EntryDetail> entryDetails;

}
