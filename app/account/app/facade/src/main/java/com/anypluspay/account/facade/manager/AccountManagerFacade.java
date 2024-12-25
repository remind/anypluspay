package com.anypluspay.account.facade.manager;

import com.anypluspay.account.facade.manager.dto.InnerAccountAddRequest;
import com.anypluspay.account.facade.manager.dto.OuterAccountAddRequest;
import com.anypluspay.account.facade.manager.dto.OuterAccountAddResponse;
import com.anypluspay.commons.response.ResponseResult;

import java.util.List;

/**
 * 账户管理相关
 *
 * @author wxj
 * 2023/12/22
 */
public interface AccountManagerFacade {

    /**
     * 创建外部账户
     *
     * @param request
     * @return
     */
    ResponseResult<String> createOuterAccount(OuterAccountAddRequest request);

    /**
     * 创建外部账户
     *
     * @param requests
     * @return
     */
    ResponseResult<List<OuterAccountAddResponse>> createOuterAccount(List<OuterAccountAddRequest> requests);

    /**
     * 创建内部账户
     *
     * @param request
     * @return
     */
    ResponseResult<String> createInnerAccount(InnerAccountAddRequest request);
}
