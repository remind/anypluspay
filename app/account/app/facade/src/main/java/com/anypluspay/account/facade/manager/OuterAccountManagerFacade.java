package com.anypluspay.account.facade.manager;

import com.anypluspay.account.facade.ApiConstants;
import com.anypluspay.account.facade.manager.request.OuterAccountRequest;
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
 * @author wxj
 * 2025/1/7
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "OuterAccountManagerFacade")
public interface OuterAccountManagerFacade {

    String PREFIX = "/outer-account-manager";

    /**
     * 创建外部账户
     *
     * @param request 请求参数
     * @return  创建结果
     */
    @PostMapping(PREFIX + "/create")
    OuterAccountResponse create(@RequestBody @Validated(AddValidate.class) OuterAccountRequest request);

    /**
     * 批量创建外部账户
     *
     * @param requests 请求参数
     * @return  创建结果
     */
    @PostMapping(PREFIX + "/create-batch")
    List<OuterAccountResponse> create(@RequestBody List<OuterAccountRequest> requests);

    /**
     * 修改外部账户
     *
     * @param request 请求参数
     */
    @PostMapping(PREFIX + "/update")
    void update(@RequestBody @Validated(UpdateValidate.class) OuterAccountRequest request);


    /**
     * 变更账户禁止状态
     *
     * @param accountNo 账户编号
     * @param denyStatusCode 账户状态编码
     */
    @GetMapping(PREFIX + "/change-deny-status")
    void changeDenyStatus(@RequestParam String accountNo, @RequestParam String denyStatusCode);

    /**
     * 查询外部账户详情
     * @param accountNo 账户号
     * @return  结果信息
     */
    @GetMapping(PREFIX + "/detail")
    OuterAccountResponse detail(@RequestParam String accountNo);

    /**
     * 根据会员编号和账户类型查询
     * @param memberId  会员编号
     * @param accountType   账户类型
     * @return
     */
    @GetMapping(PREFIX + "/query-by-member-account-type")
    OuterAccountResponse queryByMemberAndAccountTypeId(@RequestParam String memberId, @RequestParam String accountType);
}
