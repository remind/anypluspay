package com.anypluspay.account.application.entry;

import com.anypluspay.account.domain.repository.BufferedRuleRepository;
import com.anypluspay.account.facade.dto.AccountingRequest;
import com.anypluspay.commons.lang.types.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * 常规入账测试
 * @author wxj
 * 2023/12/21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountEntryServiceTest extends AccountEntryBaseTest {

    @MockitoBean
    private BufferedRuleRepository bufferedRuleRepository;

    @Before
    public void before() {
        when(bufferedRuleRepository.isExists(any(), any())).thenReturn(false);
    }

    @Test
    public void outer2OuterSuccess() {
        AccountingRequest request = buildAccountingRequest("200100200110000000215600001", "200100200110000000315600001", new Money(1));
        accountEntryService.process(request);
        assertResult(request);
    }

    @Test
    public void outerToInnerSuccess() {
        AccountingRequest request = buildAccountingRequest("200100200110000000215600001", "40010010011560002", new Money(1));
        accountEntryService.process(request);
        assertResult(request);

    }

    @Test
    public void innerToInnerSuccess() {
        AccountingRequest request = buildAccountingRequest("40010010011560001", "40010010011560002", new Money(1));
        accountEntryService.process(request);
        assertResult(request);
    }

    @Test
    public void innerToOuterSuccess() {
        AccountingRequest request = buildAccountingRequest("40010010011560001", "200100200110000000215600001", new Money(1));
        accountEntryService.process(request);
        assertResult(request);
    }

}
