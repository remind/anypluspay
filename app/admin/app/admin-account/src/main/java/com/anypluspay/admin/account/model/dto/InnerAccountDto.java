package com.anypluspay.admin.account.model.dto;

import com.anypluspay.component.web.json.std.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/1/5
 */
@Data
public class InnerAccountDto {

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 科目号
     */
    private String titleCode;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 当前余额方向 D:借，C:贷
     */
    private String currentBalanceDirection;

    /**
     * 账户余额方向 D:借，C:贷，0:双向
     */
    private String balanceDirection;

    /**
     * 货币类型
     */
    private String currencyCode;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime gmtModified;
}
