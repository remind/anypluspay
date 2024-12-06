package com.anypluspay.channel.infra.persistence.bizorder;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.infra.persistence.dataobject.BizOrderDO;

/**
 * @author wxj
 * 2024/8/20
 */
public interface BizOrderDalOperator<T extends BaseBizOrder> {

    /**
     * 加载
     * @param bizOrderDO
     * @return
     */
    T load(BizOrderDO bizOrderDO);

    /**
     * 保存
     * @param bizOrder
     */
    void store(T bizOrder);

    /**
     * 修改
     * @param bizOrder
     */
    void reStore(T bizOrder);
}
