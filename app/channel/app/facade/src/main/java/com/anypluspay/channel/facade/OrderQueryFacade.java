package com.anypluspay.channel.facade;

import com.anypluspay.channel.facade.result.FundResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 订单结果查询
 *
 * @author wxj
 * 2024/8/23
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "OrderQueryFacade")
public interface OrderQueryFacade {

    String PREFIX = "/order";

    /**
     * 根据订单号查询，如果是处理中状态，则会提交到机构查询
     *
     * @param orderId     订单号
     * @param isInstQuery 是否提交到机构查询
     * @return 订单结果
     */
    @GetMapping(PREFIX + "/query-by-order-id")
    FundResult queryByOrderId(@RequestParam String orderId, @RequestParam boolean isInstQuery);


    /**
     * 根据订单号查询，如果是处理中状态，则需要提交给渠道查询
     *
     * @param instOrderId 机构订单号
     * @param isInstQuery 是否提交到机构查询
     * @return 订单结果
     */
    @GetMapping(PREFIX + "/query-by-inst-order-id")
    FundResult queryByInstOrderId(@RequestParam Long instOrderId, @RequestParam boolean isInstQuery);
}
