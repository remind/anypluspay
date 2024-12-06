package com.anypluspay.channel.domain.repository;

import com.anypluspay.channel.domain.result.ApiResultCode;

/**
 * @author wxj
 * 2024/7/13
 */
public interface ApiResultRepository {

    void store(ApiResultCode apiResultCode);

    ApiResultCode load(String fundChannelCode, String apiTypeCode, String apiCode, String apiSubCode);
}
