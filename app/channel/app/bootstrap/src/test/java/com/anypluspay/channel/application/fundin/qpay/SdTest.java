package com.anypluspay.channel.application.fundin.qpay;

import com.anypluspay.channel.application.FundInBaseTest;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.test.TestConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wxj
 * 2024/12/17
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SdTest extends FundInBaseTest {

    @Test
    public void testSuccess() {
        FundInRequest fundInDTO = buildRequest(TestConstants.S, TestConstants.S);
        fundInDTO.setPayMethod("qpay");
        FundResult fundResult = fundInFacade.apply(fundInDTO);
        validateBySdSuccess(fundResult);
    }

}
