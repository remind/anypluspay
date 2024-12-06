package com.anypluspay.channel.facade;

import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;

/**
 * 入款处理
 * @author wxj
 * 2024/9/24
 */
public interface FundInFacade {

    /**
     * 申请入款
     * @param request
     * @return
     */
    FundResult apply(FundInRequest request);
}
