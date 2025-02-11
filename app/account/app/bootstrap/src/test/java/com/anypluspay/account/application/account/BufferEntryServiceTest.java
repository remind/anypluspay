package com.anypluspay.account.application.account;

import com.anypluspay.account.AccountEntryBaseTest;
import com.anypluspay.account.domain.detail.AccountDetail;
import com.anypluspay.account.domain.detail.BufferedDetail;
import com.anypluspay.account.domain.repository.BufferedDetailRepository;
import com.anypluspay.account.domain.repository.BufferedRuleRepository;
import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.commons.lang.types.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * 缓冲入账测试
 *
 * @author wxj
 * 2025/1/3
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BufferEntryServiceTest extends AccountEntryBaseTest {

    @MockitoBean
    private BufferedRuleRepository bufferedRuleRepository;

    @Autowired
    private BufferedDetailRepository bufferedDetailRepository;

    private static final String bufferAccountNo = "40010010011560001";

    @Before
    public void before() {
        when(bufferedRuleRepository.isExists(eq(bufferAccountNo), any())).thenReturn(true);
        when(bufferedRuleRepository.isExists(argThat(argument -> !bufferAccountNo.equals(argument)), any())).thenReturn(false);
    }

    /**
     * 测试入账缓冲
     * 1条正常1条缓冲，缓冲不执行
     */
    @Test
    public void testProcessBuffer() {
        AccountingRequest request = buildAccountingRequest(bufferAccountNo, "40010010011560002", new Money(1));
        accountOperationService.process(request);
        assetBufferDetail(request, 1, 1);
    }

    private void assetBufferDetail(AccountingRequest request, int bufferCount, int normalCount) {
        List<AccountDetail> accountDetails = assertRequest(request.getRequestNo());
        Assert.assertEquals(normalCount, accountDetails.size());
        List<BufferedDetail> bufferedDetails = bufferedDetailRepository.loadByRequestNo(request.getRequestNo());
        Assert.assertEquals(bufferCount, bufferedDetails.size());

        request.getEntryDetails().forEach(entryDetail -> {
            if (entryDetail.getAccountNo().equals(bufferAccountNo)) {
                BufferedDetail bufferedDetail = bufferedDetailRepository.load(entryDetail.getVoucherNo());
                Assert.assertNotNull(bufferedDetail);
                Assert.assertEquals(bufferedDetail.getRequestNo(), request.getRequestNo());
                Assert.assertEquals(bufferedDetail.getAmount(), entryDetail.getAmount());
            } else {
                List<AccountDetail> entryAccountDetails = accountDetails.stream().filter(accountDetail -> accountDetail.getVoucherNo().equals(entryDetail.getVoucherNo())).toList();
                Assert.assertEquals(1, entryAccountDetails.size());
                assertAccountDetail(entryDetail, entryAccountDetails.get(0));
            }
        });
    }
}
