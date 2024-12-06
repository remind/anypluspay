package com.anypluspay.admin.dao.mapper;

import com.anypluspay.admin.model.order.FundInOrderDetailDto;
import com.anypluspay.admin.model.query.Order.FundInOrderQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * 入款订单查询
 * @author wxj
 * 2024/11/13
 */
public interface FundInOrderQueryMapper {

    /**
     * 分页查询
     * @param query 查询参数
     * @param page  分页参数
     * @return  查询结果
     */
    IPage<FundInOrderDetailDto> pageQuery(@Param("query") FundInOrderQuery query, @Param("page") IPage page);
}
