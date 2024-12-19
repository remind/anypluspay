package com.anypluspay.admin.model;

import com.anypluspay.admin.model.order.*;
import lombok.Data;

import java.util.List;

/**
 * 联合查询结果
 *
 * @author wxj
 * 2024/11/14
 */
@Data
public class UnionQueryResult {

    /**
     * 业务单号
     */
    private BizOrderDto bizOrder;

    /**
     * 入款订单
     */
    private FundInOrderDto fundInOrder;


    /**
     * 退款订单
     */
    private List<RefundOrderDto> refundOrders;

    /**
     * 机构订单
     */
    private List<InstOrderDto> instOrders;

    /**
     * 机构过程订单
     */
    private List<InstCommandOrderDto> instProcessOrders;
}
