package com.anypluspay.admin.channel.model.query;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

/**
 * 支付渠道查询
 * @author wxj
 * 2024/11/12
 */
@Data
public class FundChannelQuery extends PageQuery {

    /**
     * 渠道编码
     */
    private String code;

    /**
     * 渠道名称
     */
    private String name;

    /**
     * 是否可用
     */
    private Boolean enable;

}
