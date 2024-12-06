package com.anypluspay.channel.domain.bizorder.fund;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.terminal.Terminal;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 资金类订单
 *
 * @author wxj
 * 2024/6/27
 */
@Data
public class FundInOrder extends BaseBizOrder {

    /**
     * 支付机构
     */
    private String payInst;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 终端信息
     */
    private Terminal terminal;

    /**
     * 路由字段信息，会被用来在路由中做计算，如卡类型、对公对私
     */
    private Map<String, String> routeExtra = new HashMap<>();

    public FundInOrder() {
        super(RequestType.FUND_IN);
    }
}
