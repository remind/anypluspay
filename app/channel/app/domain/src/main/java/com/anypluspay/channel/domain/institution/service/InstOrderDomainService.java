package com.anypluspay.channel.domain.institution.service;

import cn.hutool.core.collection.CollectionUtil;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.repository.InstCommandOrderRepository;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.InstOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构单领域服务
 * @author wxj
 * 2024/8/23
 */
@Service
public class InstOrderDomainService {

    @Autowired
    private InstCommandOrderRepository instCommandOrderRepository;

    /**
     * 创建指令单
     * @param instOrder
     * @param channelApiType
     * @return
     */
    public InstCommandOrder createCommand(InstOrder instOrder, ChannelApiType channelApiType) {
        InstCommandOrder instCommandOrder = new InstCommandOrder();
        instCommandOrder.setInstOrderId(instOrder.getInstOrderId());
        instCommandOrder.setFundChannelCode(instOrder.getFundChannelCode());
        instCommandOrder.setApiType(channelApiType);
        instCommandOrder.setStatus(InstOrderStatus.PROCESSING);
        instCommandOrderRepository.store(instCommandOrder);
        return instCommandOrder;
    }

    /**
     * 转人工
     * @param channelApiContext
     * @param instOrder
     * @param instCommandOrder
     * @return
     */
    public InstCommandOrder turnToManual(ChannelApiContext channelApiContext, InstOrder instOrder, InstCommandOrder instCommandOrder) {
        ChannelApiType manualApiType = getManualApiType(channelApiContext, instCommandOrder);
        InstCommandOrder manualProcessOrder = null;
        if (manualApiType != null) {
            manualProcessOrder = createCommand(instOrder, manualApiType);
        }
        return manualProcessOrder;
    }

    /**
     * 获取主指令单
     * @param instOrder
     * @return
     */
    public InstCommandOrder loadMainProcessOrder(InstOrder instOrder) {
        List<InstCommandOrder> instCommandOrders = instCommandOrderRepository.loadByInstOrderId(instOrder.getInstOrderId());
        if (CollectionUtil.isNotEmpty(instCommandOrders)) {
            return instCommandOrders.stream().filter(instProcessOrder -> instProcessOrder.getApiType() == instOrder.getApiType()).findFirst().orElse(null);
        }
        return null;
    }

    /**
     * 获取人工指令api类型
     * @param channelApiContext
     * @param instCommandOrder
     * @return
     */
    private ChannelApiType getManualApiType(ChannelApiContext channelApiContext, InstCommandOrder instCommandOrder) {
        if (instCommandOrder.getStatus() == InstOrderStatus.FAILED
                && !ChannelApiType.isManual(instCommandOrder.getApiType())) {
            if (channelApiContext.getChannelApiType() == ChannelApiType.SINGLE_REFUND) {
                return ChannelApiType.MANUAL_REFUND;
            }
        }
        return null;
    }

}
