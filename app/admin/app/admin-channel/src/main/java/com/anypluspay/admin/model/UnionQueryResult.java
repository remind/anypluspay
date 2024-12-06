package com.anypluspay.admin.model;

import com.anypluspay.admin.model.order.BizOrderDto;
import com.anypluspay.admin.model.order.FundInOrderDto;
import com.anypluspay.admin.model.order.InstOrderDto;
import com.anypluspay.admin.model.order.InstProcessOrderDto;
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
     * 机构订单
     */
    private List<InstOrderDto> instOrders;

    /**
     * 机构过程订单
     */
    private List<InstProcessOrderDto> instProcessOrders;
}
