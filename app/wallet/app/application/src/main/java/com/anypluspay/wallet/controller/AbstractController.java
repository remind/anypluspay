package com.anypluspay.wallet.controller;

import com.anypluspay.wallet.domain.login.LoginMember;

/**
 * @author wxj
 * 2025/6/24
 */
public class AbstractController {

    public LoginMember getLoginMember() {
        LoginMember loginMember = new LoginMember();
        loginMember.setMemberId("123432");
        return loginMember;
    }

    public String getLoginMemberId() {
        return getLoginMember().getMemberId();
    }
}
