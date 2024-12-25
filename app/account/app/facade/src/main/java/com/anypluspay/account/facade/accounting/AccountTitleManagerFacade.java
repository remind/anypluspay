package com.anypluspay.account.facade.accounting;

import com.anypluspay.account.facade.accounting.dto.AccountTitleAddRequest;
import com.anypluspay.commons.response.ResponseResult;

/**
 * @author wxj
 * 2023/12/16
 */
public interface AccountTitleManagerFacade {

    /**
     * 创建科目
     * @param request
     * @return
     */
    ResponseResult<String> createAccountTitle(AccountTitleAddRequest request);

    /**
     * 更新科目
     * @param request
     * @return
     */
    ResponseResult<String> updateAccountTitle(AccountTitleAddRequest request);
}
