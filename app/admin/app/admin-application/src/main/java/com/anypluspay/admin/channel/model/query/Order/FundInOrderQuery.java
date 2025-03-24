package com.anypluspay.admin.channel.model.query.Order;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 入款订单查询
 * @author wxj
 * 2024/11/13
 */
@Data
public class FundInOrderQuery extends PageQuery {

    /**
     * 支付单号
     */
    private String orderId;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间开始
     */
    private LocalDateTime gmtCreateStart;

    /**
     * 创建时间结束
     */
    private LocalDateTime gmtCreateEnd;
}
