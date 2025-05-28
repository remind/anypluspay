package com.anypluspay.payment.types.member;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 会员状态
 *
 * @author wxj
 * 2025/5/27
 */
@Getter
public enum MemberStatus implements CodeEnum {

    INACTIVE("0", "未激活"),
    NORMAL("1", "正常"),
    SLEEP("2", "休眠"),
    CANCEL("3", "销户");

    /**
     * 编码
     */
    private final String code;

    /**
     * 名称
     */
    private final String displayName;

    MemberStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}
