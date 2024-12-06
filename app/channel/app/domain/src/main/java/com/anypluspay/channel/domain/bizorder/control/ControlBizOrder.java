package com.anypluspay.channel.domain.bizorder.control;

import com.anypluspay.channel.domain.bizorder.BaseBizOrder;
import com.anypluspay.channel.types.enums.RequestRootType;
import com.anypluspay.channel.types.enums.RequestType;
import lombok.Data;
import org.springframework.util.Assert;

/**
 * @author wxj
 * 2024/7/16
 */
@Data
public class ControlBizOrder extends BaseBizOrder {

    /**
     * 渠道编码
     */
    private String fundChannelCode;

    public ControlBizOrder(RequestType requestType) {
        super(requestType);
        Assert.isTrue(requestType.getRequestRootType().equals(RequestRootType.CONTROL), "requestType must be CONTROL");
    }
}
