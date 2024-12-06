package com.anypluspay.channel.application.refund;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.application.FundServiceBaseTest;
import com.anypluspay.channel.domain.bizorder.fund.FundInOrder;
import com.anypluspay.channel.facade.RefundFacade;
import com.anypluspay.channel.facade.request.RefundRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channel.types.enums.RefundType;
import com.anypluspay.channel.types.order.BizOrderStatus;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.types.Money;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxj
 * 2024/7/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RefundTest extends FundServiceBaseTest {

    @Autowired
    private RefundFacade refundService;

    @Test
    public void refundSuccess() {
        FundResult fundResult = refundService.apply(buildRefundOrder(TestConstants.S, null));
        log.info(ToStringBuilder.reflectionToString(fundResult));
        Assert.assertEquals(BizOrderStatus.SUCCESS, fundResult.getStatus());
    }

    @Test
    public void refundProcessing() {
        FundResult refundResult = refundService.apply(buildRefundOrder(TestConstants.P, null));
        log.info(ToStringBuilder.reflectionToString(refundResult));
        Assert.assertEquals(BizOrderStatus.PROCESSING, refundResult.getStatus());
    }

    @Test
    public void refundFail() {
        FundResult refundResult = refundService.apply(buildRefundOrder(TestConstants.F, null));
        log.info(ToStringBuilder.reflectionToString(refundResult));
        Assert.assertEquals(BizOrderStatus.FAILED, refundResult.getStatus());
    }

    @Test(expected = BizException.class)
    public void refundAmountGreaterThanOrigAmount() {
        refundService.apply(buildRefundOrder(TestConstants.S, new Money(104)));
    }

    @Test(expected = BizException.class)
    public void multipleRefundAmountGreaterThanOrigAmount() {
        FundResult origResult = buildNormalFundOrder();
        FundResult fundResult1 = refundService.apply(buildRefundOrder(origResult, TestConstants.S, new Money(40)));
        Assert.assertEquals(BizOrderStatus.SUCCESS, fundResult1.getStatus());

        FundResult fundResult2 = refundService.apply(buildRefundOrder(origResult, TestConstants.S, new Money(63)));
        Assert.assertEquals(BizOrderStatus.SUCCESS, fundResult2.getStatus());
    }

    @Test
    public void multipleRefundSuccess() {
        FundResult origResult = buildNormalFundOrder();
        FundResult fundResult1 = refundService.apply(buildRefundOrder(origResult, TestConstants.S, new Money(40)));
        Assert.assertEquals(BizOrderStatus.SUCCESS, fundResult1.getStatus());

        FundResult fundResult2 = refundService.apply(buildRefundOrder(origResult, TestConstants.S, new Money(60)));
        Assert.assertEquals(BizOrderStatus.SUCCESS, fundResult2.getStatus());
    }

    private RefundRequest buildRefundOrder(String testFlag, Money refundAmount) {
        return buildRefundOrder(buildNormalFundOrder(), testFlag, refundAmount);
    }

    private RefundRequest buildRefundOrder(FundResult originOrder, String testFlag, Money refundAmount) {
        FundInOrder origOrder = (FundInOrder) bizOrderRepository.load(originOrder.getOrderId());

        RefundRequest refundDTO = new RefundRequest();
        refundDTO.setRequestId(randomId());
        refundDTO.setMemberId("100000004");

        refundDTO.setRefundType(RefundType.PAYER_REFUND);
        refundDTO.setOrigOrderId(origOrder.getOrderId());
        refundDTO.setOrigRequestId(origOrder.getRequestId());
        refundDTO.setAmount(refundAmount == null ? origOrder.getAmount() : refundAmount);
        refundDTO.setReason("测试退款");
        Map<String, String> instExtInfo = new HashMap<>();
        instExtInfo.put(ExtKey.TEST_FLAG.getCode(), JSONUtil.toJsonStr(new TestFlag(testFlag, null)));
        refundDTO.setInstExtra(instExtInfo);
        return refundDTO;
    }

}