package com.anypluspay.account.facade;

import com.anypluspay.account.facade.request.AccountingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 账务相关接口
 *
 * @author wxj
 * 2023/12/20
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, contextId = ApiConstants.SERVICE_NAME + "AccountFacade")
public interface AccountingFacade {

    String PREFIX = "/account";

    /**
     * 入账申请
     * 有异常即失败，否则为成功
     *
     * @param accountingRequest 请求参数
     */
    @PostMapping(PREFIX + "/apply")
    void apply(@RequestBody AccountingRequest accountingRequest);

}
