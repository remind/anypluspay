package com.anypluspay.channel.application.fundout;

import com.anypluspay.channel.application.FundOutBaseTest;
import com.anypluspay.channel.facade.request.FundOutRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.test.TestConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wxj
 * 2024/12/18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SingleFundOutTest extends FundOutBaseTest {

    @Test
    public void fundOutSuccess() {
        FundOutRequest request = buildRequest(TestConstants.S);
        FundResult fundResult = fundOutFacade.apply(request);
        log.info(ToStringBuilder.reflectionToString(fundResult));
        validateFundOutOrder(fundResult);
    }
}
