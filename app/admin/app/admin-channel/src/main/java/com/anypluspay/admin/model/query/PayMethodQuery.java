package com.anypluspay.admin.model.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * 支付方式查询
 * @author wxj
 * 2024/11/12
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
