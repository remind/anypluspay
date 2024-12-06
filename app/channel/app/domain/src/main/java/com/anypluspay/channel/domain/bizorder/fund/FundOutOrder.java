package com.anypluspay.channel.domain.bizorder.fund;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.types.CardInfo;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;

import java.util.Map;

/**
 * @author wxj
 * 2024/7/18
 */
@Data
public class FundOutOrder extends BaseBizOrder {

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 出款卡信息
     */
    private CardInfo cardInfo;

    /**
     * 路由字段信息
     */
    private Map<String, String> routeExtra;

    public FundOutOrder() {
        super(RequestType.FUND_OUT);
    }
}
