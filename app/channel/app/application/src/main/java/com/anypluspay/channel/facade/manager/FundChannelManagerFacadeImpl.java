package com.anypluspay.channel.facade.manager;

import com.anypluspay.channel.domain.channel.api.ChannelApiParam;
import com.anypluspay.channel.domain.repository.channel.ChannelApiParamRepository;
import com.anypluspay.channel.facade.manager.response.ChannelApiParamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxj
 * 2025/6/5
 */
@RestController
public class FundChannelManagerFacadeImpl implements FundChannelManagerFacade {

    @Autowired
    private ChannelApiParamRepository channelApiParamRepository;

    @Override
    public ChannelApiParamResponse getChannelApiParam(String paramId) {
        ChannelApiParam channelApiParam = channelApiParamRepository.load(paramId);
        ChannelApiParamResponse response = new ChannelApiParamResponse();
        if (channelApiParam != null) {
            response.setId(paramId);
            response.setParam(channelApiParam.getParam());
        }
        return response;
    }
}
