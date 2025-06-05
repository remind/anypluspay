package com.anypluspay.channel.domain.bizorder;

import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.Extension;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础业务单
 *
 * @author wxj
 * 2024/6/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseBizOrder extends Entity {

    /**
     * 渠道订单ID
     */
    private String orderId;

    /**
     * 请求号
     */
    private String requestId;

    /**
     * 合作方ID
     */
    private String partnerId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 请求类型
     */
    private final RequestType requestType;

    /**
     * 状态
     */
    private BizOrderStatus status;

    /**
     * 最后使用的机构单ID
     */
    private Long instOrderId;

    /**
     * 机构扩展信息，仅渠道网关API要使用传到机构，如微信的openid
     */
    private Extension instExt;

    /**
     * 扩展信息
     */
    private Extension extension = new Extension();

    public BaseBizOrder(RequestType requestType) {
        this.requestType = requestType;
    }

}
