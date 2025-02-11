package com.anypluspay.account.domain.repository;

import com.anypluspay.account.domain.detail.BufferedDetail;

import java.util.List;

/**
 * @author wxj
 * 2023/12/21
 */
public interface BufferedDetailRepository {

    void store(List<BufferedDetail> bufferedDetails);

    void reStore(BufferedDetail bufferedDetail);

    BufferedDetail lock(String voucherNo);

    BufferedDetail load(String voucherNo);

    List<BufferedDetail> loadByRequestNo(String requestNo);
}
