package com.anypluspay.payment.domain.member;

import com.anypluspay.commons.lang.Entity;
import com.anypluspay.payment.types.member.MemberStatus;
import com.anypluspay.payment.types.member.MemberType;
import lombok.Data;

/**
 * 会员
 *
 * @author wxj
 * 2025/5/27
 */
@Data
public class Member extends Entity {

    /**
     * 会员号
     */
    private String memberId;

    /**
     * 会员名称
     */
    private String name;

    /**
     * 会员类型
     */
    private MemberType memberType;

    /**
     * 会员状态
     */
    private MemberStatus status;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}
