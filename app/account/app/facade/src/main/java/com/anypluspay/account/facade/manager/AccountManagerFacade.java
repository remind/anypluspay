package com.anypluspay.account.facade.manager;

import com.anypluspay.account.facade.ApiConstants;
import com.anypluspay.account.facade.manager.dto.InnerAccountRequest;
import com.anypluspay.account.facade.manager.dto.OuterAccountAddRequest;
import com.anypluspay.account.facade.manager.response.InnerAccountResponse;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
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
    String createInnerAccount(@RequestBody @Validated(AddValidate.class) InnerAccountRequest request);

    /**
     * 修改内部账户
     *
     * @param request 请求参数
     */
    @PostMapping(PREFIX + "/update-inner-account")
    void updateInnerAccount(@RequestBody @Validated(UpdateValidate.class) InnerAccountRequest request);

    /**
     * 删除内部账户
     *
     * @param accountNo 账户编号
     */
    @GetMapping(PREFIX + "/delete-inner-account")
    void deleteInnerAccount(@RequestParam String accountNo);

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


    /**
     * 查询内部账户
     * @param accountNo 账户号
     * @return  结果信息
     */
    @GetMapping(PREFIX + "/query-inner-account")
    InnerAccountResponse queryInnerAccount(@RequestParam String accountNo);
}
