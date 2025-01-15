package com.anypluspay.admin.account.dto;

import com.anypluspay.component.web.json.std.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 外部户
 * @author wxj
 * 2025/1/5
 */
@Data
public class OuterAccountDto {

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
     * 会员号
     */
    private String memberId;

    /**
     * 禁止状态
     */
    private String denyStatus;

    /**
     * 禁止状态名称
     */
    private String denyStatusName;

    /**
     * 账户属性 1:对私，2:对公
     */
    private String accountAttribute;

    /**
     * 账户属性名称
     */
    private String accountAttributeName;

    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 当前余额方向 D:借，C:贷
     */
    private String currentBalanceDirection;

    /**
     * 当前余额方向名称
     */
    private String currentBalanceDirectionName;

    /**
     * 账户余额方向 D:借，C:贷，T:双向
     */
    private String balanceDirection;

    /**
     * 账户余额方向名称
     */
    private String balanceDirectionName;

    /**
     * 货币类型
     */
    private String currencyCode;

    /**
     * 子户
     */
    private List<OuterSubAccountDto> outerSubAccounts;

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
