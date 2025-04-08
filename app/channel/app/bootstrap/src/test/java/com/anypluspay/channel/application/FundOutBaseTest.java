package com.anypluspay.channel.application;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.domain.repository.BizOrderRepository;
import com.anypluspay.channel.facade.FundOutFacade;
import com.anypluspay.channel.facade.OrderQueryFacade;
import com.anypluspay.channel.facade.request.FundOutRequest;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.mock.FundChannelMock;
import com.anypluspay.channel.types.ChannelExtKey;
import com.anypluspay.channel.types.enums.CompanyOrPersonal;
import com.anypluspay.channel.types.result.CsResultCode;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2024/12/18
 */
public class FundOutBaseTest extends FundChannelMock {

    @Autowired
    protected BizOrderRepository bizOrderRepository;

    @Autowired
    protected OrderQueryFacade orderQueryFacade;

    @Autowired
    protected FundOutFacade fundOutFacade;

    protected FundResult createFundOutOrder() {
        FundResult fundResult = fundOutFacade.apply(buildRequest(TestConstants.S));
        validateFundOutOrder(fundResult);
        return fundResult;
    }

    protected void validateFundOutOrder(FundResult fundResult) {
        Assert.assertNotNull(fundResult);
//        Assert.assertTrue(fundResult.isSuccess());
        Assert.assertEquals(CsResultCode.WAIT_SUBMIT_INST.getCode(), fundResult.getUnityCode());
    }

    protected FundOutRequest buildRequest(String testDFlag) {
        return buildRequest(testDFlag, null);
    }

    protected FundOutRequest buildRequest(String testDFlag, String testQFlag) {
        FundOutRequest request = new FundOutRequest();
        request.setMemberId("100000004");
        request.setRequestId(randomId());
        request.setBankCode("TB1");
        request.setPayMethod("balance");
        request.setAccountNo("testAccountNo");
        request.setAccountName("testAccountName");
        request.setAccountType(CompanyOrPersonal.PERSONAL);
        request.setAmount(new Money(100));
        Extension instExt = new Extension();
        instExt.add(ChannelExtKey.TEST_FLAG.getCode(), JSONUtil.toJsonStr(new TestFlag(testDFlag, testQFlag)));
        return request;
    }
}
