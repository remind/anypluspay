package com.anypluspay.payment.infra.persistence.mapper;

import com.anypluspay.payment.types.trade.query.TradeBillDto;
import com.anypluspay.payment.types.trade.query.TradeBillQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * @author wxj
 * 2025/6/29
 */
public interface TradeBillQueryMapper {

    IPage<TradeBillDto> queryBill(@Param("query") TradeBillQuery query, @Param("page") IPage page);
}
