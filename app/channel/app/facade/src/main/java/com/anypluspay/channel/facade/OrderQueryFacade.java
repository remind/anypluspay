package com.anypluspay.channel.facade;

import com.anypluspay.channel.facade.result.FundResult;

/**
 * 订单结果查询
 * @author wxj
 * 2024/8/23
 */
public interface OrderQueryFacade {

    /**
     * 根据订单号查询，如果是处理中状态，则会提交到机构查询
     *
     * @param orderId     订单号
     * @param isInstQuery 是否提交到机构查询
     * @return  订单结果
     */
    FundResult queryByOrderId(String orderId, boolean isInstQuery);


    /**
     * 根据订单号查询，如果是处理中状态，则需要提交给渠道查询
     *
     * @param instOrderId   机构订单号
     * @param isInstQuery   是否提交到机构查询
     * @return  订单结果
     */
    FundResult queryByInstOrderId(Long instOrderId, boolean isInstQuery);
}
