package com.anypluspay.admin.channel.model.query.Order;

import com.anypluspay.commons.response.page.PageQuery;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wxj
 * 2024/11/22
 */
@Data
public class InstOrderQuery extends PageQuery {

    /**
     * 机构订单号
     */
    private String instOrderId;

    /*
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
