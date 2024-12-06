package com.anypluspay.channel.domain.institution.service;

import com.anypluspay.channel.domain.institution.InstOrder;
import com.anypluspay.channel.domain.institution.InstProcessOrder;

/**
 * @author wxj
 * 2024/8/23
 */
public interface InstOrderService {

    InstProcessOrder loadMainProcessOrder(InstOrder instOrder);
}
