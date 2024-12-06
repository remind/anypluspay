package com.anypluspay.account.domain.service;

import com.anypluspay.account.domain.detail.AccountDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2023/12/21
 */
@Component
@Slf4j
public class AccountDetailService {

    /**
     * 是否缓冲
     * @param accountDetail
     * @return
     */
    public boolean isBuffer(AccountDetail accountDetail) {
        return false;
    }
}
