package com.anypluspay.channel.domain.bizorder;

import com.anypluspay.channel.domain.institution.InstCommandOrder;
import com.anypluspay.channel.domain.institution.InstOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单上下文
 * @author wxj
 * 2024/8/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderContext {

    /**
     * 业务订单
     */
    private BaseBizOrder bizOrder;

    /**
     * 机构订单
     */
    private InstOrder instOrder;

    /**
     * 机构过程订单
     */
    private InstCommandOrder instCommandOrder;
}
