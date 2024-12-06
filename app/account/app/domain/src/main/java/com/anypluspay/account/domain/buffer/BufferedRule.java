package com.anypluspay.account.domain.buffer;

import com.anypluspay.account.types.buffer.BufferedRuleStatus;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.commons.lang.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2023/12/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BufferedRule extends Entity {

    /**
     * 规则ID
     */
    private String ruleId;

    /**
     * 帐号
     */
    private String accountNo;

    /**
     * 借贷标志，为空则均支持
     */
    private CrDr crDr;

    /**
     * 是否有效状态
     */
    private BufferedRuleStatus status;

    /**
     * 备注
     */
    private String memo;

}
