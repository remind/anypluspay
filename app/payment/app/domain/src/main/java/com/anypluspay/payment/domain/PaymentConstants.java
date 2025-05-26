package com.anypluspay.payment.domain;

/**
 * @author wxj
 * 2024/1/25
 */
public interface PaymentConstants {

    /**
     * 过渡户
     */
    String TRANSITION_ACCOUNT = "20200010011560001";

    /**
     * 账户用户
     */
    String INNER_MEMBER_ID = "inner_member";

    /**
     * 某些场景的默认值
     */
    String DEFAULT_NULL_VALUE = "0";

    /**
     * 默认过期时间，分钟
     */
    Integer DEFAULT_EXPIRE_MINUTES = 30;
}
