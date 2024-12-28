package com.anypluspay.channel.application;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.mock.FundChannelMock;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.facade.FundInFacade;
import com.anypluspay.channel.facade.OrderQueryFacade;
import com.anypluspay.channel.facade.request.FundInRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.ExtUtil;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 入款单元测试，渠道模拟，订单入库
 *
 * @author wxj
 * 2024/12/16
 */
public class FundInBaseTest extends FundChannelMock {

    @Autowired
    protected FundInFacade fundInFacade;

    @Autowired
    protected BizOrderRepository bizOrderRepository;

    @Autowired
    protected OrderQueryFacade orderQueryFacade;

    protected FundResult createSuccessFundInOrder() {
        FundInRequest fundInDTO = buildRequest(TestConstants.S, TestConstants.S);
        fundInDTO.setPayMethod("qpay");
        return fundInFacade.apply(fundInDTO);
    }

    protected FundResult createProcessFundOrder() {
        FundInRequest fundInDTO = buildRequest(TestConstants.S, TestConstants.S);
        FundResult result = fundInFacade.apply(fundInDTO);
        validateBySignSuccess(result);
        return result;
    }

    protected FundInRequest buildRequest(String testDFlag) {
        return buildRequest(testDFlag, null);
    }

    protected FundInRequest buildRequest(String testDFlag, String testQFlag) {
        FundInRequest request = new FundInRequest();
        request.setMemberId("100000004");
        request.setRequestId(randomId());
        request.setPayInst("TB1");
        request.setPayMethod("online_bank");
        request.setGoodsDesc("test");
        request.setAmount(new Money(100));
        Map<String, String> instExtra = Map.of(ExtKey.TEST_FLAG.getCode(), JSONUtil.toJsonStr(new TestFlag(testDFlag, testQFlag)));
        request.setInstExtra(instExtra);
        return request;
    }

    /**
     * 新增路由参数
     *
     * @param request
     * @param extKey
     * @param value
     */
    protected void addRouteExtra(FundInRequest request, ExtKey extKey, String value) {
        ExtUtil.addValue(request.getRouteExtra(), extKey, value);
    }

    /**
     * 签名成功验证
     *
     * @param result
     */
    protected void validateBySignSuccess(FundResult result) {
        Assert.assertNotNull(result);
        Assert.assertEquals(BizOrderStatus.PROCESSING, result.getStatus());
        Assert.assertNotNull(result.getUnityCode());
        Assert.assertNotNull(result.getUnityMessage());
        Assert.assertNotNull(result.getInstRequestNo());
        Assert.assertNotNull(result.getInstResponseNo());
        Assert.assertNotNull(result.getResponseExtra());
        Assert.assertNotNull(ExtUtil.getStringValue(ExtKey.INST_URL, result.getResponseExtra()));
    }

    /**
     * 单笔扣款成功验证
     *
     * @param result
     */
    protected void validateBySdSuccess(FundResult result) {
        Assert.assertNotNull(result);
        Assert.assertEquals(BizOrderStatus.SUCCESS, result.getStatus());
        Assert.assertNotNull(result.getUnityCode());
        Assert.assertNotNull(result.getUnityMessage());
        Assert.assertNotNull(result.getInstRequestNo());
        Assert.assertNotNull(result.getInstResponseNo());
    }

    protected void validateByQuerySuccess(FundResult result) {
        Assert.assertNotNull(result);
        Assert.assertEquals(BizOrderStatus.SUCCESS, result.getStatus());
        Assert.assertNotNull(result.getUnityCode());
        Assert.assertNotNull(result.getUnityMessage());
    }
}