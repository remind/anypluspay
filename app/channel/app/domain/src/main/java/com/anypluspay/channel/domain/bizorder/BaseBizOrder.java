package com.anypluspay.channel.domain.bizorder;

import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.lang.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

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
    private String instOrderId;

    /**
     * 扩展字段
     */
    private Map<String, String> extra = new HashMap<>();

    /**
     * 机构扩展信息，渠道网关API要使用的
     */
    private Map<String, String> instExtra = new HashMap<>();

    public BaseBizOrder(RequestType requestType) {
        this.requestType = requestType;
    }

    public void addExtValue(ExtKey extKey, String value) {
        if (extra == null) {
            extra = new HashMap<>();
        }
        extra.put(extKey.getCode(), value);
    }

    public void addInstExtValue(ExtKey extKey, String value) {
        if (instExtra == null) {
            instExtra = new HashMap<>();
        }
        instExtra.put(extKey.getCode(), value);
    }
}
