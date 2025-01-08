package com.anypluspay.account.facade;

import com.anypluspay.account.facade.request.TransferRequest;

/**
 * 转账
 *
 * @author wxj
 * 2025/1/7
 */
public interface TransferFacade {

    /**
     * 转账
     *
     * @param request 转账请求
     */
    void transfer(TransferRequest request);
}
