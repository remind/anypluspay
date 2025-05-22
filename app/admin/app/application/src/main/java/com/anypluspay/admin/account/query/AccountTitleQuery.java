package com.anypluspay.admin.account.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * @author wxj
 * 2024/12/26
 */
@Data
public class AccountTitleQuery extends PageQuery {

    /**
     * 科目代码
     */
    private String code;

    /**
     * 科目名称
     */
    private String name;

    /**
     * 是否有效
     */
    private Boolean enable;
}
