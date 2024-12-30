package com.anypluspay.admin;

import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.channel.InstAbility;
import com.anypluspay.channel.types.channel.MaintainTimeType;
import com.anypluspay.channel.types.enums.CardType;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.commons.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wxj
 * 2024/12/30
 */
@Component
public class AdminChannelRunner implements ApplicationRunner {

    @Autowired
    private List<Class<? extends CodeEnum>> codeEnumList;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        codeEnumList.add(InstAbility.class);
        codeEnumList.add(ChannelApiType.class);
        codeEnumList.add(RequestType.class);
        codeEnumList.add(CardType.class);
        codeEnumList.add(MaintainTimeType.class);
        codeEnumList.add(InstOrderStatus.class);
    }
}
