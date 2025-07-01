package com.anypluspay.payment.facade.trade;

import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.facade.ApiConstants;
import com.anypluspay.payment.types.trade.query.TradeBillDto;
import com.anypluspay.payment.types.trade.query.TradeBillQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 交易账单
 *
 * @author wxj
 * 2025/6/29
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "TradeBillFacade")
public interface TradeBillFacade {

    String PREFIX = "/trade-bill";

    /**
     * 查询
     *
     * @param query
     * @return
     */
    @GetMapping(PREFIX + "/query")
    PageResult<TradeBillDto> query(@SpringQueryMap TradeBillQuery query);

}
