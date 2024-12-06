package com.anypluspay.account.domain.repository;

import com.anypluspay.account.domain.buffer.BufferedRule;
import com.anypluspay.account.types.enums.CrDr;

/**
 * @author wxj
 * 2023/12/25
 */
public interface BufferedRuleRepository {
    void store(BufferedRule rule);

    void reStore(BufferedRule rule);

    boolean isExists(String accountNo, CrDr crDr);

}
