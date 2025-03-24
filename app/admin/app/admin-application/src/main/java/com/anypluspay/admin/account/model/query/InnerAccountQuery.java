package com.anypluspay.admin.account.model.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * @author wxj
 * 2025/1/5
 */
@Data
public class InnerAccountQuery extends PageQuery {

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
}
