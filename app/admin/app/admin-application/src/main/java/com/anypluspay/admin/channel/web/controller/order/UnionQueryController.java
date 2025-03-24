//package com.anypluspay.admin.channel.web.controller.order;
//
//import cn.hutool.core.collection.CollectionUtil;
//import cn.hutool.core.lang.Assert;
//import com.anypluspay.admin.channel.dao.OrderQueryDao;
//import com.anypluspay.admin.channel.model.UnionQueryResult;
//import com.anypluspay.admin.channel.model.order.BizOrderDto;
//import com.anypluspay.admin.channel.model.order.InstOrderDto;
//import com.anypluspay.admin.channel.model.order.RefundOrderDto;
//import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;
//import com.anypluspay.channel.infra.persistence.dataobject.InstOrderDO;
//import com.anypluspay.channel.infra.persistence.mapper.InstOrderMapper;
//import com.anypluspay.channel.types.enums.RequestType;
//import com.anypluspay.commons.response.ResponseResult;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 联合查询
// *
// * @author wxj
// * 2024/11/14
// */
//@RestController
//@RequestMapping("/union-query")
//public class UnionQueryController {
//
//    @Autowired
//    private OrderQueryDao orderQueryDao;
//
//    @Autowired
//    private InstOrderMapper instOrderMapper;
//
//    /**
//     * 查询
//     *
//     * @param orderId   单号
//     * @param orderType 单号类型
//     * @return
//     */
//    @GetMapping("/query")
//    public ResponseResult<UnionQueryResult> query(String orderId, String orderType) {
//        UnionQueryResult result = new UnionQueryResult();
//        BizOrderDto bizOrderDto = getBizOrder(orderId, orderType);
//        Assert.notNull(bizOrderDto, "订单不存在");
//        List<String> allOrderIds = new ArrayList<>();
//        allOrderIds.add(bizOrderDto.getOrderId());
//
//        result.setBizOrder(bizOrderDto);
//        result.setFundInOrder(orderQueryDao.getFundInOrderByOrderId(result.getBizOrder().getOrderId()));
//
//        List<RefundOrderDto> refundOrders = orderQueryDao.getRefundOrderByBizOrigOrderId(bizOrderDto.getOrderId());
//        if (CollectionUtil.isNotEmpty(refundOrders)) {
//            result.setRefundOrders(refundOrders);
//            allOrderIds.addAll(refundOrders.stream().map(RefundOrderDto::getOrderId).toList());
//        }
//        result.setInstOrders(orderQueryDao.getInstOrderByBizOrderId(allOrderIds));
//        if (CollectionUtil.isNotEmpty(result.getInstOrders())) {
//            result.setInstProcessOrders(orderQueryDao.getInstProcessOrderByInstOrderId(
//                    result.getInstOrders().stream().map(InstOrderDto::getInstOrderId).toList()));
//        }
//        return ResponseResult.success(result);
//    }
//
//    private BizOrderDto getBizOrder(String orderId, String orderType) {
//        BizOrderDto bizOrder = null;
//        if ("1".equals(orderType)) {
//            // 渠道订单号
//            bizOrder = orderQueryDao.getBizOrderByOrderId(orderId);
//            if (bizOrder != null && bizOrder.getRequestType() == RequestType.REFUND) {
//                bizOrder = orderQueryDao.getOrigBizOrderByRefundOrderId(bizOrder.getOrderId());
//            }
//        } else if ("2".equals(orderType)) {
//            // 请求号
//            LambdaQueryWrapper<BizOrderDO> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(BizOrderDO::getRequestId, orderId);
//            bizOrder = orderQueryDao.getBizOrderByRequestId(orderId);
//        } else if ("3".equals(orderType)) {
//            // 机构订单号
//            InstOrderDO instOrderDO = instOrderMapper.selectById(orderId);
//            if (instOrderDO != null) {
//                bizOrder = orderQueryDao.getBizOrderByOrderId(instOrderDO.getBizOrderId());
//            }
//        } else if ("4".equals(orderType)) {
//            // 机构请求号
//            LambdaQueryWrapper<InstOrderDO> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(InstOrderDO::getInstRequestNo, orderId);
//            InstOrderDO instOrderDO = instOrderMapper.selectOne(queryWrapper);
//            if (instOrderDO != null) {
//                bizOrder = orderQueryDao.getBizOrderByOrderId(instOrderDO.getBizOrderId());
//            }
//        }
//        return bizOrder;
//    }
//
//}
