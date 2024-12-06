package com.anypluspay.channel.domain.repository;

import com.anypluspay.channel.domain.result.UnityResultCode;

/**
 * @author wxj
 * 2024/7/13
 */
public interface UnityResultCodeRepository {

    UnityResultCode load(String unityCode);
}
