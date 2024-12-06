package com.anypluspay.channel.facade;

import com.anypluspay.channel.facade.request.FundOutRequest;
import com.anypluspay.channel.facade.result.FundResult;

/**
 * 出款
 * @author wxj
 * 2024/7/18
 */
public interface FundOutFacade {

    /**
     * 出款
     * @param request
     * @return
     */
    FundResult apply(FundOutRequest request);
}
