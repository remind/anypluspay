package com.anypluspay.channel.mock;

import cn.hutool.core.util.ObjectUtil;
import com.anypluspay.channel.base.AbstractBaseTest;
import com.anypluspay.channel.domain.repository.ApiResultRepository;
import com.anypluspay.channel.infra.persistence.convertor.ApiResultCodeDalConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.*;
import com.anypluspay.channel.infra.persistence.mapper.ChannelApiMapper;
import com.anypluspay.channel.infra.persistence.mapper.ChannelMaintainMapper;
import com.anypluspay.channel.infra.persistence.mapper.ChannelSupportInstMapper;
import com.anypluspay.channel.infra.persistence.mapper.FundChannelMapper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * 资金渠道模拟数据
 * @author wxj
 * 2024/12/16
 */
public class FundChannelMock extends AbstractBaseTest {

    @MockBean
    private FundChannelMapper dalMapper;

    @MockBean
    private ChannelApiMapper channelApiMapper;

    @MockBean
    private ChannelSupportInstMapper channelSupportInstMapper;

    @MockBean
    private ChannelMaintainMapper channelMaintainMapper;

    @MockBean
    protected ApiResultRepository apiResultRepository;

    @Autowired
    private ApiResultCodeDalConvertor apiResultCodeDalConvertor;

    protected static final String TEST_CHANNEL_CODE = "LOCALBANK001";

    @Before
    public void mock() {
        when(dalMapper.selectList(any())).thenReturn(getCsvData("mock/tc_fund_channel.csv", FundChannelDO.class));
        when(channelApiMapper.selectList(any())).thenReturn(getCsvData("mock/tc_channel_api.csv", ChannelApiDO.class));
        when(channelSupportInstMapper.selectList(any())).thenReturn(getCsvData("mock/tc_channel_support_inst.csv", ChannelSupportInstDO.class));
        when(channelMaintainMapper.selectList(any())).thenReturn(getCsvData("mock/tc_channel_maintain.csv", ChannelMaintainDO.class));

        // apiResultCode的查询需要根据查询条件返回，而mapper上的查询条件太复杂，因此mock到repository，而不是直接mock mapper
        // 如果有结果码不存在的情况，还需要mock store方法
        List<ApiResultCodeDO> apiResultCodeDOS = getCsvData("mock/tc_api_result_code.csv", ApiResultCodeDO.class);
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            for (ApiResultCodeDO apiResultCodeDO : apiResultCodeDOS) {
                if (eq(args[0], apiResultCodeDO.getChannelCode())
                        && eq(args[1], apiResultCodeDO.getApiType())
                        && eq(args[2], apiResultCodeDO.getInstApiCode())
                        && eq(args[3], apiResultCodeDO.getInstApiSubCode())) {
                    return apiResultCodeDalConvertor.toEntity(apiResultCodeDO);
                }
            }
            return null;
        }).when(apiResultRepository).load(anyString(), anyString(), anyString(), any());
    }

    private boolean eq(Object o1, Object o2) {
        boolean ret = ObjectUtil.equals(o1, o2);
        if (!ret) {
            return ObjectUtil.isEmpty(o1) && ObjectUtil.isEmpty(o2);
        }
        return true;
    }
}
