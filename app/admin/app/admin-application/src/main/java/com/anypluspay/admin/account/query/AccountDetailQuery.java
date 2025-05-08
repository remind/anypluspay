package com.anypluspay.admin.account.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

import java.util.Date;

/**
 * 入账明细查询参数
 * @author wxj
 * 2025/5/8
 */
@Data
public class AccountDetailQuery extends PageQuery {

    /**
     * 账户
     */
    private String accountNo;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;
}
