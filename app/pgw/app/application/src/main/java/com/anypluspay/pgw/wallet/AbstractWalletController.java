package com.anypluspay.pgw.wallet;

/**
 * @author wxj
 * 2025/6/26
 */
public abstract class AbstractWalletController {

    public LoginMember getLoginMember() {
        LoginMember loginMember = new LoginMember();
        loginMember.setMemberId("100000002");
        loginMember.setDefaultAccountNo("200100200110000000215600001");
        loginMember.setAuthName("张三");
        loginMember.setAuthIdNo("110101199001011111");
        loginMember.setAuthMobile("13800000001");
        return loginMember;
    }
}
