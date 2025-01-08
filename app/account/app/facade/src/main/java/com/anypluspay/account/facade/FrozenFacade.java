package com.anypluspay.account.facade;

import com.anypluspay.account.facade.request.FrozenRequest;
import com.anypluspay.account.facade.request.UnFrozenRequest;

/**
 * 冻结解冻接口
 *
 * @author wxj
 * 2025/1/8
 */
public interface FrozenFacade {

    /**
     * 冻结
     *
     * @param request 请求参数
     */
    void frozen(FrozenRequest request);

    /**
     * 解冻
     *
     * @param request 请求参数
     */
    void unFrozen(UnFrozenRequest request);
}
