package com.anypluspay.account.application.entry;

import com.anypluspay.account.domain.repository.BufferedRuleRepository;
import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.facade.dto.EntryDetail;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.commons.lang.types.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * 缓冲入账测试
 * @author wxj
 * 2025/1/3
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BufferEntryServiceTest extends AccountEntryBaseTest {

    @MockitoBean
    private BufferedRuleRepository bufferedRuleRepository;

    private static final String bufferAccountNo = "40010010011560001";

    @Before
    public void before() {
        when(bufferedRuleRepository.isExists(eq(bufferAccountNo), any())).thenReturn(true);
        when(bufferedRuleRepository.isExists(argThat(argument -> !bufferAccountNo.equals(argument)), any())).thenReturn(false);
    }

    /**
     * 测试入账缓冲
     */
    @Test
    public void testProcessBuffer() {
        AccountingRequest request = buildAccountingRequest(bufferAccountNo, "40010010011560002", new Money(1));
        accountOperationService.process(request);
        assertAccountResult(request);
    }

    private String createBufferDetail() {
        AccountingRequest request = new AccountingRequest();
        request.setAccountingDate("20231221");
        request.setRequestNo(getUUID());
        EntryDetail entryDetail1 = new EntryDetail();
        entryDetail1.setVoucherNo(getUUID());
        entryDetail1.setAccountNo("200100200110000000215600001");
        entryDetail1.setSuiteNo("1");
        entryDetail1.setAmount(new Money(1));
        entryDetail1.setCrDr(CrDr.CREDIT);
        entryDetail1.setMemo("测试入账1");

        EntryDetail entryDetail2 = new EntryDetail();
        entryDetail2.setVoucherNo(getUUID());
        entryDetail2.setAccountNo("40010010011560001");
        entryDetail2.setSuiteNo("1");
        entryDetail2.setAmount(new Money(1));
        entryDetail2.setCrDr(CrDr.DEBIT);
        entryDetail2.setMemo("测试入账2");

        request.setEntryDetails(List.of(entryDetail1, entryDetail2));
        accountOperationService.process(request);
        return entryDetail2.getVoucherNo();
    }
}
