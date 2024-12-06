package com.anypluspay.channel.facade.control;

import com.anypluspay.channel.facade.control.request.ControlRequest;
import com.anypluspay.channel.facade.result.ChannelResult;

/**
 * @author wxj
 * 2024/9/24
 */
public interface ControlFacade {

    ChannelResult apply(ControlRequest controlRequest);
}
