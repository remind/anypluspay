package com.anypluspay.admin.account;

import com.anypluspay.account.types.accounting.AccountTitleScope;
import com.anypluspay.account.types.accounting.AccountTitleType;
import com.anypluspay.account.types.enums.BalanceDirection;
import com.anypluspay.commons.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * account初始化
 * @author wxj
 * 2024/12/30
 */
@Component
public class AdminAccountRunner implements ApplicationRunner {

    @Autowired
    private List<Class<? extends CodeEnum>> codeEnumList;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        codeEnumList.add(AccountTitleType.class);
        codeEnumList.add(BalanceDirection.class);
        codeEnumList.add(AccountTitleScope.class);
    }
}
