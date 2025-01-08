package com.anypluspay.account.facade.manager;

import com.anypluspay.account.facade.ApiConstants;
import com.anypluspay.account.facade.manager.request.InnerAccountRequest;
import com.anypluspay.account.facade.manager.response.InnerAccountResponse;
import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 账户管理相关
 *
 * @author wxj
 * 2023/12/22
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "InnerAccountManagerFacade")
public interface InnerAccountManagerFacade {

    String PREFIX = "/inner-account-manager";

    /**
     * 创建内部账户
     *
     * @param request 请求参数
     * @return  创建结果
     */
    @PostMapping(PREFIX + "/create-inner-account")
    String create(@RequestBody @Validated(AddValidate.class) InnerAccountRequest request);

    /**
     * 修改内部账户
     *
     * @param request 请求参数
     */
    @PostMapping(PREFIX + "/update-inner-account")
    void update(@RequestBody @Validated(UpdateValidate.class) InnerAccountRequest request);

    /**
     * 删除内部账户
     *
     * @param accountNo 账户编号
     */
    @GetMapping(PREFIX + "/delete-inner-account")
    void delete(@RequestParam String accountNo);


    /**
     * 查询内部账户详情
     * @param accountNo 账户号
     * @return  结果信息
     */
    @GetMapping(PREFIX + "/detail")
    InnerAccountResponse detail(@RequestParam String accountNo);
}
