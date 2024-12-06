package com.anypluspay.account.domain;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 入账事务
 *
 * @author wxj
 * 2023/12/19
 */
@Data
public class AccountTransaction extends Entity {

    /**
     * 入账请求流水号，唯一，一般由支付按统一规则生成
     */
    private String requestNo;

    /**
     * 会计日
     **/
    private String accountingDate;

}
