package com.anypluspay.pgw.wallet.member;

import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.pgw.wallet.AbstractWalletController;
import com.anypluspay.pgw.wallet.member.response.BalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录会员信息查询
 * @author wxj
 * 2025/6/26
 */
@RestController
@RequestMapping("/wallet/login-member")
public class LoginMemberController extends AbstractWalletController {

    @Autowired
    private OuterAccountManagerFacade outerAccountManagerFacade;

    /**
     * 获取登录会员的余额信息
     * @return
     */
    @GetMapping("/balance")
    public ResponseResult<BalanceResponse> balance() {
        OuterAccountResponse outerAccountResponse = outerAccountManagerFacade.detail(getLoginMember().getDefaultAccountNo());
        BalanceResponse response = new BalanceResponse();
        response.setBalance(outerAccountResponse.getBalance());
        response.setAvailableBalance(outerAccountResponse.getAvailableBalance());
        response.setFrozenBalance(outerAccountResponse.getBalance().subtract(outerAccountResponse.getAvailableBalance()));
        return ResponseResult.success(response);
    }
}
