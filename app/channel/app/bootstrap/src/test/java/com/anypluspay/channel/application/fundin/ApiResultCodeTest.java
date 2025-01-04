package com.anypluspay.channel.application.fundin;

import com.anypluspay.channel.application.FundInBaseTest;
import com.anypluspay.channel.domain.result.ApiResultCode;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.api.sign.SignResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author wxj
 * 2024/12/16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ApiResultCodeTest extends FundInBaseTest {

    @MockitoBean(name = "localBankSignGateway")
    private ChannelGateway channelGateway;

    @Captor
    private ArgumentCaptor<ApiResultCode> captor;

    private static final String MOCK_API_CODE = "m_code";
    private static final String MOCK_API_SUB_CODE = "m_sub_code";
    private static final String MOCK_MESSAGE = "mock_message";

    @Test
    public void notExistsBySign() {
        SignResult result = new SignResult();
        result.setSuccess(true);
        result.setApiCode(MOCK_API_CODE);
        result.setApiSubCode(MOCK_API_SUB_CODE);
        result.setApiMessage(MOCK_MESSAGE);
        when(channelGateway.call(any())).thenReturn(result);
        FundInRequest request = buildRequest(TestConstants.S);
        FundResult fundResult = fundInFacade.apply(request);
        verify(apiResultRepository).store(captor.capture());
        ApiResultCode apiResultCode = captor.getValue();
        Assert.assertNotNull(apiResultCode);
        Assert.assertEquals(MOCK_API_CODE, apiResultCode.getInstApiCode());
        Assert.assertEquals(MOCK_API_SUB_CODE, apiResultCode.getInstApiSubCode());
        Assert.assertEquals(MOCK_MESSAGE, apiResultCode.getInstApiMessage());
        validateByUnknown(fundResult, apiResultCode);
    }

    protected void validateByUnknown(FundResult result, ApiResultCode apiResultCode) {
        Assert.assertNotNull(result);
        Assert.assertEquals(BizOrderStatus.PROCESSING, result.getStatus());
        Assert.assertNotNull(result.getUnityCode());
        Assert.assertEquals(apiResultCode.getInstApiCode(), result.getUnityCode());
        Assert.assertEquals(apiResultCode.getInstApiMessage(), result.getUnityMessage());
    }
}
