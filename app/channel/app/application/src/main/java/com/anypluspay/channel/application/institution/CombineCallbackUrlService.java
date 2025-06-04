package com.anypluspay.channel.application.institution;

import com.anypluspay.channel.domain.SystemConfig;
import com.anypluspay.channel.domain.bizorder.ChannelApiContext;
import com.anypluspay.channel.domain.channel.ChannelFullInfo;
import com.anypluspay.channel.domain.repository.channel.ChannelFullInfoRepository;
import com.anypluspay.channel.types.channel.ChannelApiType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 组装回调url
 *
 * @author wxj
 * 2024/12/9
 */
@Service
public class CombineCallbackUrlService {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private ChannelFullInfoRepository channelFullInfoRepository;

    /**
     * 获取后端异步通知地址
     *
     * @param channelApiContext api上下文
     * @return 地址
     */
    public String getServerNotifyUrl(ChannelApiContext channelApiContext) {
        if (channelApiContext.getChannelApiType() == ChannelApiType.SIGN
                || channelApiContext.getChannelApiType() == ChannelApiType.SINGLE_DEBIT) {
            ChannelFullInfo channelFullInfo = channelFullInfoRepository.getChannelFullInfo(channelApiContext.getChannelCode());
            if (channelFullInfo.getChannelApis().stream()
                    .anyMatch(channelApi -> channelApi.getType() == ChannelApiType.VERIFY_SIGN)) {
                return systemConfig.getNotifyUrl() + "/notify/" + ChannelApiType.VERIFY_SIGN.getCode() + "/" + channelApiContext.getChannelCode();
            }
        } else if (channelApiContext.getChannelApiType() == ChannelApiType.SINGLE_REFUND) {
            ChannelFullInfo channelFullInfo = channelFullInfoRepository.getChannelFullInfo(channelApiContext.getChannelCode());
            if (channelFullInfo.getChannelApis().stream()
                    .anyMatch(channelApi -> channelApi.getType() == ChannelApiType.REFUND_VERIFY_SIGN)) {
                return systemConfig.getNotifyUrl() + "/notify/" + ChannelApiType.REFUND_VERIFY_SIGN.getCode() + "/" + channelApiContext.getChannelCode();
            }
        }
        return "";
    }

    /**
     * 获取前端回调地址
     *
     * @param channelApiContext api上下文
     * @return 地址
     */
    public String getReturnPageUrl(ChannelApiContext channelApiContext) {
        if (channelApiContext.getChannelApiType() == ChannelApiType.SIGN
                || channelApiContext.getChannelApiType() == ChannelApiType.SINGLE_DEBIT) {
            ChannelFullInfo channelFullInfo = channelFullInfoRepository.getChannelFullInfo(channelApiContext.getChannelCode());
            if (channelFullInfo.getChannelApis().stream()
                    .anyMatch(channelApi -> channelApi.getType() == ChannelApiType.VERIFY_SIGN)) {
                return systemConfig.getNotifyUrl() + "/return-page/" + ChannelApiType.VERIFY_SIGN.getCode() + "/" + channelApiContext.getChannelCode();
            }
        } else if (channelApiContext.getChannelApiType() == ChannelApiType.SINGLE_REFUND) {
            ChannelFullInfo channelFullInfo = channelFullInfoRepository.getChannelFullInfo(channelApiContext.getChannelCode());
            if (channelFullInfo.getChannelApis().stream()
                    .anyMatch(channelApi -> channelApi.getType() == ChannelApiType.REFUND_VERIFY_SIGN)) {
                return systemConfig.getNotifyUrl() + "/return-page/" + ChannelApiType.REFUND_VERIFY_SIGN.getCode() + "/" + channelApiContext.getChannelCode();
            }
        }
        return "";
    }
}
