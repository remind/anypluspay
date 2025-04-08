package com.anypluspay.payment.domain.repository;

import com.anypluspay.payment.types.funds.FundDetail;

/**
 * @author wxj
 * 2025/4/3
 */
public interface FundDetailRepository {

    FundDetail load(String detailId);
}
