package com.anypluspay.pgw.wallet;

import lombok.Data;

/**
 * 登录会员
 * @author wxj
 * 2025/6/29
 */
@Data
public class LoginMember {

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 默认账户
     */
    private String defaultAccountNo;
}
