package com.anypluspay.pgw.wallet.controller;

import com.anypluspay.account.facade.manager.OuterAccountManagerFacade;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.pgw.wallet.AbstractWalletController;
import com.anypluspay.pgw.wallet.response.member.AccountResponse;
import com.anypluspay.pgw.wallet.response.member.BalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录会员信息查询
 *
 * @author wxj
 * 2025/6/26
 */
@RestController
@RequestMapping("/wallet/member")
public class MemberController extends AbstractWalletController {

    @Autowired
    private OuterAccountManagerFacade outerAccountManagerFacade;

    /**
     * 获取登录会员的余额信息
     *
     * @return
     */
    @GetMapping("/balance")
    public ResponseResult<BalanceResponse> balance() {
        OuterAccountResponse outerAccountResponse = outerAccountManagerFacade.detail(getLoginMember().getDefaultAccountNo());
        return ResponseResult.success(toBalanceResponse(outerAccountResponse));
    }

    /**
     * 获取账户列表
     * @return
     */
    @GetMapping("/account-list")
    public ResponseResult<List<AccountResponse>> accountList() {
        List<OuterAccountResponse> accountResponses = outerAccountManagerFacade.queryByMemberId(getLoginMember().getMemberId());
        return ResponseResult.success(accountResponses.stream().map(accountResponse -> {
            AccountResponse response = new AccountResponse();
            response.setAccountNo(accountResponse.getAccountNo());
            response.setAccountName(accountResponse.getAccountName());
            response.setAccountType(accountResponse.getAccountType());
            response.setBalance(toBalanceResponse(accountResponse));
            return response;
        }).toList());
    }

    private BalanceResponse toBalanceResponse(OuterAccountResponse outerAccountResponse) {
        BalanceResponse response = new BalanceResponse();
        response.setBalance(outerAccountResponse.getBalance());
        response.setAvailableBalance(outerAccountResponse.getAvailableBalance());
        response.setFrozenBalance(outerAccountResponse.getBalance().subtract(outerAccountResponse.getAvailableBalance()));
        return response;
    }

}
