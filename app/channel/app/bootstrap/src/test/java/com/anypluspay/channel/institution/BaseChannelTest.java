package com.anypluspay.channel.institution;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.anypluspay.channel.domain.channel.ChannelFullInfo;
import com.anypluspay.channel.domain.channel.ChannelSupportInst;
import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channel.domain.channel.fund.FundChannel;
import com.anypluspay.channel.types.channel.ChannelApiProtocol;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.enums.CardType;
import com.anypluspay.channelgateway.test.TestOnlineBankQueryGateway;
import com.anypluspay.channelgateway.test.TestOnlineBankRefundGateway;
import com.anypluspay.channelgateway.test.TestOnlineBankSignGateway;
import com.anypluspay.channelgateway.test.TestOnlineBankVerifySignGateway;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2024/7/3
 */
public class BaseChannelTest {

    protected ChannelFullInfo channelFullInfo;

    @Before
    public void setUp() {
        initMockChannel();
    }

    private void initMockChannel() {
        channelFullInfo = new ChannelFullInfo();
        FundChannel fundChannel = new FundChannel();
        fundChannel.setCode("TEST001");
        fundChannel.setName("测试网银渠道");
        channelFullInfo.setFundChannel(fundChannel);

        List<ChannelSupportInst> channelSupportInstList = new ArrayList<>();
        channelSupportInstList.add(buildFundChannelSupportInst("ABC", CardType.DEBIT));
        channelSupportInstList.add(buildFundChannelSupportInst("ICBC", null));
        channelFullInfo.setChannelSupportInst(channelSupportInstList);

        channelFullInfo.setChannelApis(List.of(buildFundChannelApi(ChannelApiType.SIGN, StrUtil.lowerFirst(TestOnlineBankSignGateway.class.getSimpleName()))
                , buildFundChannelApi(ChannelApiType.SINGLE_QUERY, StrUtil.lowerFirst(TestOnlineBankQueryGateway.class.getSimpleName()))
                , buildFundChannelApi(ChannelApiType.VERIFY_SIGN, StrUtil.lowerFirst(TestOnlineBankVerifySignGateway.class.getSimpleName()))
                , buildFundChannelApi(ChannelApiType.SINGLE_REFUND, StrUtil.lowerFirst(TestOnlineBankRefundGateway.class.getSimpleName()))));

//        Mockito.when(channelFullInfoRepository.getAllAvailableChannels(Mockito.any(), Mockito.any())).thenReturn(List.of(channelFullInfo));
//        Mockito.when(channelFullInfoRepository.getChannelFullInfo(Mockito.any())).thenReturn(channelFullInfo);
    }

    private ChannelSupportInst buildFundChannelSupportInst(String instCode, CardType cardType) {
        ChannelSupportInst channelSupportInst = new ChannelSupportInst();
        channelSupportInst.setTargetInstCode(instCode);
        channelSupportInst.setCardType(cardType);
        return channelSupportInst;
    }

    protected ChannelApi buildFundChannelApi(ChannelApiType type, String address) {
        ChannelApi channelApi = new ChannelApi();
        channelApi.setChannelCode("wxpay");
        channelApi.setAddress(address);
        channelApi.setType(type);
        channelApi.setProtocol(ChannelApiProtocol.BEAN);
        return channelApi;
    }

    protected String randomId() {
        return UUID.fastUUID().toString(true);
    }

}
