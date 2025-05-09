package com.anypluspay.admin.account.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * @author wxj
 * 2025/1/7
 */
@Data
public class OuterAccountQuery extends PageQuery {

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 会员ID
     */
    private String memberId;
}
