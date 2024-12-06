package com.anypluspay.channel.application.fundin;

import com.anypluspay.channel.application.FundServiceBaseTest;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.terminal.AppOS;
import com.anypluspay.commons.terminal.AppTerminal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author wxj
 * 2024/9/18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class WxpayTest extends FundServiceBaseTest {

    @Test
    public void applySignSuccess() {
        FundResult fundResult = signRequest();
        log.info(ToStringBuilder.reflectionToString(fundResult));
        Assert.assertEquals(BizOrderStatus.PROCESSING, fundResult.getStatus());
        Assert.assertNotNull(fundResult.getResponseExtra());
    }

    @Test
    public void querySuccess() {
        FundResult fundResult = signRequest();
        FundResult queryResult = orderQueryFacade.queryByOrderId(fundResult.getOrderId(), true);
        log.info(ToStringBuilder.reflectionToString(queryResult));
        Assert.assertEquals(BizOrderStatus.PROCESSING, queryResult.getStatus());
    }

    private FundResult signRequest() {
        return fundInFacade.apply(buildFundOrder());
    }

    protected FundInRequest buildFundOrder() {
        FundInRequest fundInDTO = new FundInRequest();
        fundInDTO.setMemberId("100000004");
        fundInDTO.setRequestId(randomId());
        fundInDTO.setRouteExtra(null);
        fundInDTO.setPayInst("WXPAY");
        fundInDTO.setPayMethod("jsapi");
        fundInDTO.setGoodsDesc("test");
        fundInDTO.setAmount(new Money(100));

        AppTerminal terminal = new AppTerminal();
        terminal.setAppOS(AppOS.IOS);
        terminal.setVersion("1.0.0");
        fundInDTO.setTerminal(terminal);

        Map<String, String> instExtra = Map.of("openId", "ohzYF4wvwUNkB4focDyOoHdDwxoE");
        fundInDTO.setInstExtra(instExtra);

        return fundInDTO;
    }
}
