package com.anypluspay.channel.route;

import com.anypluspay.channel.mock.FundChannelMock;
import com.anypluspay.channel.application.route.ChannelRouteService;
import com.anypluspay.channel.application.route.RouteParam;
import com.anypluspay.channel.types.enums.RequestType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wxj
 * 2024/7/18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ChannelRouteServiceTest extends FundChannelMock {

    @Autowired
    private ChannelRouteService channelRouteService;

    @Test
    public void testRoteOneSuccess() {
        RouteParam routeParam = new RouteParam();
        routeParam.setRequestType(RequestType.FUND_IN);
        routeParam.setPayInst("TB1");
        routeParam.setPayModel("qpay");
        Assert.assertNotNull(channelRouteService.routeOne(routeParam));
    }
}
