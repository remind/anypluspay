package com.anypluspay.admin.account.response;

import com.anypluspay.commons.lang.std.CustomerLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/5/8
 */
@Data
public class OuterAccountDetailResponse {

    /**
     * 凭证号
     */
    private String voucherNo;

    /**
     * 请求号
     */
    private String requestNo;

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 入账前余额
     */
    private String beforeBalance;

    /**
     * 入账后余额
     */
    private String afterBalance;

    /**
     * 发生金额
     */
    private String amount;

    /**
     * 操作类型，常规、冻结、解冻
     */
    private String operationType;

    /**
     * 借贷标志
     */
    private String crDr;

    /**
     * 加减方向
     */
    private String ioDirection;

    /**
     * 会计日
     */
    private String accountingDate;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;

    @JsonSerialize(using = CustomerLocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
