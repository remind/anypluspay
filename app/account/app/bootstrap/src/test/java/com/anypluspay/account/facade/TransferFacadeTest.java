package com.anypluspay.account.facade;

import com.anypluspay.account.AccountEntryBaseTest;
import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.OuterAccountDetail;
import com.anypluspay.account.facade.request.TransferRequest;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.response.GlobalResultCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wxj
 * 2025/1/7
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TransferFacadeTest extends AccountEntryBaseTest {

    @Autowired
    private TransferFacade transferFacade;

    @Test
    public void transferSuccess() {
        TransferRequest request = buildRequest("200100200110000000215600001", "200100200110000000315600001", new Money(1));
        transferFacade.transfer(request);
        assertTransferResult(request);
    }

    @Test
    public void transferHasInnerAccount1() {
        wrapperBizException(GlobalResultCode.FAIL, () -> {
            TransferRequest request = buildRequest("200100200110000000215600001", "40010010011560002", new Money(1));
            transferFacade.transfer(request);
        });
    }

    @Test
    public void transferHasInnerAccount2() {
        wrapperBizException(GlobalResultCode.FAIL, () -> {
            TransferRequest request = buildRequest("40010010011560001", "200100200110000000215600001", new Money(1));
            transferFacade.transfer(request);
        });
    }

    private TransferRequest buildRequest(String transferOutAccountNo, String transferInAccountNo, Money amount) {
        TransferRequest request = new TransferRequest();
        request.setRequestNo(randomId());
        request.setAmount(amount);
        request.setTransferInAccountNo(transferInAccountNo);
        request.setTransferOutAccountNo(transferOutAccountNo);
        request.setMemo("转账");
        return request;
    }

    protected void assertTransferResult(TransferRequest request) {
        List<AccountDetail> accountDetails = assertRequest(request.getRequestNo());
        Assert.assertEquals(2, accountDetails.size());
        accountDetails.forEach(accountDetail -> {
            assetOuterAccountDetail((OuterAccountDetail) accountDetail, request.getRequestNo());
        });
    }
}
