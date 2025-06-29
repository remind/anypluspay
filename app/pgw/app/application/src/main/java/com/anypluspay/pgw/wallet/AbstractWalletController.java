package com.anypluspay.pgw.wallet;

/**
 * @author wxj
 * 2025/6/26
 */
public abstract class AbstractWalletController {

    public LoginMember getLoginMember() {
        LoginMember loginMember = new LoginMember();
        loginMember.setMemberId("100000002");
        loginMember.setMemberName("张三");
        loginMember.setDefaultAccountNo("200100200110000000215600001");
        return loginMember;
    }
}
