package com.anypluspay.channel.domain.result.service.impl;

import com.anypluspay.channel.domain.repository.ApiResultRepository;
import com.anypluspay.channel.domain.repository.UnityResultCodeRepository;
import com.anypluspay.channel.domain.result.ApiResultCode;
import com.anypluspay.channel.domain.result.UnityResult;
import com.anypluspay.channel.domain.result.UnityResultCode;
import com.anypluspay.channel.domain.result.service.ApiResultService;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.order.InstOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/7/10
 */
@Service
public class ApiResultServiceImpl implements ApiResultService {

    @Autowired
    private ApiResultRepository apiResultRepository;

    @Autowired
    private UnityResultCodeRepository unityResultCodeRepository;

    @Override
    public UnityResult doMatch(String channelCode, ChannelApiType apiType, String instApiCode, String instApiSubCode, String instApiMessage) {
        UnityResult result = new UnityResult();
        result.setApiType(apiType);

        ApiResultCode apiResultCode = apiResultRepository.load(channelCode, apiType.getCode(), instApiCode, instApiSubCode);
        if (apiResultCode != null) {
            if (apiResultCode.isUseMapping()) {
                UnityResultCode unityResultCode = unityResultCodeRepository.load(apiResultCode.getUnityCode());
                if (unityResultCode != null) {
                    fillMatchResult(result, unityResultCode, apiResultCode);
                    return result;
                }
            }
        } else {
            addApiResultCode(channelCode, apiType, instApiCode, instApiSubCode,instApiMessage);
        }
        fillUnknownResult(result, instApiCode, instApiSubCode,instApiMessage);
        return result;
    }

    private void fillMatchResult(UnityResult result, UnityResultCode unityResultCode, ApiResultCode apiResultCode) {
        result.setMatch(true);
        result.setResultCode(unityResultCode.getCode());
        result.setResultMessage(unityResultCode.getTemplate());
        result.setStatus(apiResultCode.getResultStatus());
    }

    private void fillUnknownResult(UnityResult result, String instApiCode, String instApiSubCode, String channelResultMessage) {
        result.setMatch(false);
        result.setResultCode(instApiCode);
        result.setResultMessage(channelResultMessage);
        result.setStatus(InstOrderStatus.UNKNOWN);
    }

    private void addApiResultCode(String fundChannelCode, ChannelApiType apiType, String instApiCode, String instApiSubCode, String channelResultMessage) {
        ApiResultCode apiResultCode = new ApiResultCode();
        apiResultCode.setChannelCode(fundChannelCode);
        apiResultCode.setApiType(apiType);
        apiResultCode.setInstApiCode(instApiCode);
        apiResultCode.setInstApiSubCode(instApiSubCode);
        apiResultCode.setInstApiMessage(channelResultMessage);
        apiResultRepository.store(apiResultCode);
    }
}
