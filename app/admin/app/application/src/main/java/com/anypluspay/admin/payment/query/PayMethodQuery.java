package com.anypluspay.admin.payment.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * @author wxj
 * 2025/5/28
 */
@Data
public class PayMethodQuery extends PageQuery {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;
}
