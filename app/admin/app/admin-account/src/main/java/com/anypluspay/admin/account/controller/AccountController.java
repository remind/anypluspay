package com.anypluspay.admin.account.controller;

import com.anypluspay.account.facade.manager.AccountManagerFacade;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.commons.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户
 *
 * @author wxj
 * 2024/12/30
 */
@RestController
@RequestMapping("/account/")
public class AccountController {

    @Autowired
    private AccountManagerFacade accountManagerFacade;

    /**
     * 根据主键查询详情
     *
     * @param accountNo 主键
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResponseResult<OuterAccountResponse> detail(@RequestParam String accountNo) {
        OuterAccountResponse outerAccountDto = accountManagerFacade.queryOuterAccount(accountNo);
        return ResponseResult.success(outerAccountDto);
    }
}
