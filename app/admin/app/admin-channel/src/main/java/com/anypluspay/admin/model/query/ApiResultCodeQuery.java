package com.anypluspay.admin.model.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * @author wxj
 * 2024/12/1
 */
@Data
public class ApiResultCodeQuery extends PageQuery {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * api类型
     */
    private String apiType;

    /**
     * 是否映射
     */
    private Boolean useMapping;
}
