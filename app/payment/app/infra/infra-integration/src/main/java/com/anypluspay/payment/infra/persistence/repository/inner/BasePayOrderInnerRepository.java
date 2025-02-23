//package com.anypluspay.payment.infra.persistence.repository.inner;
//
//import com.anypluspay.payment.domain.payorder.BasePayOrder;
//import com.anypluspay.payment.infra.persistence.convertor.PayOrderDalConvertor;
//import com.anypluspay.payment.infra.persistence.dataobject.OrderExtensionDO;
//import com.anypluspay.payment.infra.persistence.dataobject.PayOrderDO;
//import com.anypluspay.payment.infra.persistence.mapper.OrderExtensionMapper;
//import com.anypluspay.payment.infra.persistence.mapper.PayOrderMapper;
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author wxj
// * 2024/1/18
// */
//@Repository
//public class BasePayOrderInnerRepository {
//
//    @Autowired
//    private PayOrderMapper dalMapper;
//
//    @Autowired
//    private PayOrderDalConvertor dalConvertor;
//
//    @Autowired
//    private OrderExtensionMapper extensionMapper;
//
//    @Autowired
//    private FundDetailInnerRepository fundDetailInnerRepository;
//
//    public List<BasePayOrder> loadByPaymentId(String paymentId) {
//        List<BasePayOrder> basePayOrders = dalConvertor.toBasePayOrder(dalMapper.selectList(buildPaymentIdQueryWrapper(paymentId)));
//        Map<String, OrderExtensionDO> orderExtensionMap = getOrderExtensionMap(paymentId);
//        basePayOrders.forEach(basePayOrder -> {
//            OrderExtensionDO orderExtensionDO = orderExtensionMap.get(basePayOrder.getPaymentId());
//            if (orderExtensionDO != null) {
//                basePayOrder.setOrderExtension(orderExtensionDO.getContent());
//            }
//        });
//        return basePayOrders;
//    }
//
//    public void store(BasePayOrder payOrder) {
//        dalMapper.insert(dalConvertor.toDo(payOrder));
//        if (StrUtil.isNotBlank(payOrder.getOrderExtension())) {
//            extensionMapper.insert(buildOrderExtensionDO(payOrder));
//        }
//        fundDetailInnerRepository.store(payOrder.getPayerDetails());
//        fundDetailInnerRepository.store(payOrder.getPayeeDetails());
//    }
//
//    public void reStore(BasePayOrder payOrder) {
//        dalMapper.updateById(dalConvertor.toDo(payOrder));
//        extensionMapper.updateById(buildOrderExtensionDO(payOrder));
//        fundDetailInnerRepository.reStore(payOrder.getPayerDetails());
//        fundDetailInnerRepository.reStore(payOrder.getPayeeDetails());
//    }
//
//    private OrderExtensionDO buildOrderExtensionDO(BasePayOrder payOrder) {
//        OrderExtensionDO orderExtensionDO = new OrderExtensionDO();
//        orderExtensionDO.setPaymentId(payOrder.getPaymentId());
//        orderExtensionDO.setOrderId(payOrder.getOrderId());
//        orderExtensionDO.setContent(payOrder.getOrderExtension());
//        return orderExtensionDO;
//    }
//
//    private Map<String, OrderExtensionDO> getOrderExtensionMap(String paymentId) {
//        List<OrderExtensionDO> orderExtensionDOs = extensionMapper.selectList(buildPaymentIdExtQueryWrapper(paymentId));
//        return orderExtensionDOs.stream().collect(Collectors.toMap(OrderExtensionDO::getPaymentId, orderExtensionDO -> orderExtensionDO));
//    }
//
//    private Wrapper<OrderExtensionDO> buildPaymentIdExtQueryWrapper(String paymentId) {
//        return new LambdaQueryWrapper<OrderExtensionDO>().eq(OrderExtensionDO::getPaymentId, paymentId);
//    }
//
//    private Wrapper<PayOrderDO> buildPaymentIdQueryWrapper(String paymentId) {
//        return new LambdaQueryWrapper<PayOrderDO>().eq(PayOrderDO::getPaymentId, paymentId);
//    }
//}
