package com.anypluspay.account.facade.manager;

import com.anypluspay.account.facade.ApiConstants;
import com.anypluspay.account.facade.manager.dto.InnerAccountAddRequest;
import com.anypluspay.account.facade.manager.dto.OuterAccountAddRequest;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 账户管理相关
 *
 * @author wxj
 * 2023/12/22
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "AccountManagerFacade")
public interface AccountManagerFacade {

    String PREFIX = "/account-manager";

    /**
     * 创建外部账户
     *
     * @param request 请求参数
     * @return  创建结果
     */
    @PostMapping(PREFIX + "/create-outer-account")
    OuterAccountResponse createOuterAccount(@RequestBody OuterAccountAddRequest request);

    /**
     * 批量创建外部账户
     *
     * @param requests 请求参数
     * @return  创建结果
     */
    @PostMapping(PREFIX + "/create-outer-account-batch")
    List<OuterAccountResponse> createOuterAccount(@RequestBody List<OuterAccountAddRequest> requests);

    /**
     * 创建内部账户
     *
     * @param request 请求参数
     * @return  创建结果
     */
    @PostMapping(PREFIX + "/create-inner-account")
    String createInnerAccount(@RequestBody InnerAccountAddRequest request);

    /**
     * 变更账户禁止状态
     *
     * @param accountNo 账户编号
     * @param denyStatusCode 账户状态编码
     */
    @PostMapping(PREFIX + "/change-deny-status")
    void changeDenyStatus(@RequestParam String accountNo, @RequestParam String denyStatusCode);

    /**
     * 查询外部账户
     * @param accountNo 账户号
     * @return  结果信息
     */
    @GetMapping(PREFIX + "/query-outer-account")
    OuterAccountResponse queryOuterAccount(@RequestParam String accountNo);
}
