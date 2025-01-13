package com.anypluspay.account.facade.manager;

import com.anypluspay.account.facade.ApiConstants;
import com.anypluspay.account.facade.manager.request.AccountTitleRequest;
import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 科目管理
 * @author wxj
 * 2023/12/16
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "AccountTitleManagerFacade")
public interface AccountTitleManagerFacade {

    String PREFIX = "/account-title";

    /**
     * 创建科目
     * @param request   请求对象
     * @return  创建结果
     */
    @PostMapping(PREFIX + "/create")
    String create(@Validated(AddValidate.class) @RequestBody AccountTitleRequest request);

    /**
     * 更新科目
     * @param request   请求对象
     * @return  更新结果
     */
    @PostMapping(PREFIX + "/update")
    boolean update(@Validated(UpdateValidate.class) @RequestBody AccountTitleRequest request);

    /**
     * 删除科目
     * @param code  科目代码
     */
    @GetMapping(PREFIX + "/delete")
    void delete(@RequestParam String code);
}
