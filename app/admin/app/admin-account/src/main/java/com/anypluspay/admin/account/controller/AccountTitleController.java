package com.anypluspay.admin.account.controller;

import com.anypluspay.account.infra.persistence.mapper.AccountTitleMapper;
import com.anypluspay.basis.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxj
 * 2024/12/24
 */
@RestController
@RequestMapping("/account/account-title")
public class AccountTitleController extends AbstractController {

    @Autowired
    private AccountTitleMapper accountTitleMapper;
}
