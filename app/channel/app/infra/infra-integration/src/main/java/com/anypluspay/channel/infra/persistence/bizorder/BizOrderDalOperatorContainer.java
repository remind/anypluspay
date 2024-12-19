package com.anypluspay.channel.infra.persistence.bizorder;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.commons.lang.utils.EnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2024/8/20
 */
@Component
public class BizOrderDalOperatorContainer {

    @Autowired
    @Qualifier("fundInDalOperator")
    private BizOrderDalOperator<? extends BaseBizOrder> fundInDalOperator;

    @Autowired
    @Qualifier("refundDalOperator")
    private BizOrderDalOperator<? extends BaseBizOrder> refundDalOperator;

    @Autowired
    @Qualifier("fundOutDalOperator")
    private BizOrderDalOperator<? extends BaseBizOrder> fundOutDalOperator;

    @SuppressWarnings("rawtypes")
    public <T extends BaseBizOrder> BizOrderDalOperator getOperator(String type) {
        RequestType requestType = EnumUtil.getByCode(RequestType.class, type);
        assert requestType != null;
        return switch (requestType) {
            case FUND_IN -> fundInDalOperator;
            case REFUND -> refundDalOperator;
            case FUND_OUT -> fundOutDalOperator;
            default -> throw new IllegalArgumentException("找不到指定类型的dal操作类");
        };
    }
}
