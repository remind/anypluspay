package com.anypluspay.channel.domain.channel.api.service;

import com.anypluspay.channel.types.channel.ChannelApiType;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/12/14
 */
@Service
public class ChannelApiDomainService {

    public ChannelApiType getQueryApiType(ChannelApiType apiType) {
        return switch (apiType) {
            case SIGN, SINGLE_DEBIT -> ChannelApiType.SINGLE_QUERY;
            case SINGLE_REFUND -> ChannelApiType.SINGLE_REFUND_QUERY;
            default -> null;
        };
    }
}
