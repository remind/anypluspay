package com.anypluspay.account.facade.accounting;

import com.anypluspay.account.facade.ApiConstants;
import com.anypluspay.account.facade.accounting.dto.AccountTitleRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 科目管理
 * @author wxj
 * 2023/12/16
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "OrderQueryFacade")
public interface AccountTitleManagerFacade {

    String PREFIX = "/account-title";

    /**
     * 创建科目
     * @param request   请求对象
     * @return  创建结果
     */
    @PostMapping(PREFIX + "/create-account-title")
    boolean createAccountTitle(@Valid @RequestBody AccountTitleRequest request);

    /**
     * 更新科目
     * @param request   请求对象
     * @return  更新结果
     */
    @PostMapping(PREFIX + "/update-account-title")
    boolean updateAccountTitle(@Valid @RequestBody AccountTitleRequest request);
}
