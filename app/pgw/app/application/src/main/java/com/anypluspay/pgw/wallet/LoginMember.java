package com.anypluspay.pgw.wallet;

import lombok.Data;

/**
 * 登录会员
 *
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
     * 默认账户
     */
    private String defaultAccountNo;

    /**
     * 身份证号码
     */
    private String authIdNo;

    /**
     * 实名姓名
     */
    private String authName;

    /**
     * 实名手机号码
     */
    private String authMobile;

}
