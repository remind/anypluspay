package com.anypluspay.account.facade;

import com.anypluspay.account.AccountEntryBaseTest;
import com.anypluspay.account.domain.repository.BufferedRuleRepository;
import com.anypluspay.account.facade.request.AccountingRequest;
import com.anypluspay.account.types.AccountResultCode;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.commons.lang.types.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author wxj
 * 2025/1/10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MultiAccountingFacadeTest extends AccountEntryBaseTest {

    @MockitoBean
    private BufferedRuleRepository bufferedRuleRepository;

    @Before
    public void before() {
        when(bufferedRuleRepository.isExists(any(), any())).thenReturn(false);
    }

    @Test
    public void outer2OuterSuccess() {
        // 1借2贷
        AccountingRequest request = new AccountingRequest();
        request.setRequestNo(randomId());
        request.setEntryDetails(
                List.of(buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000215600001", new Money(1), CrDr.CREDIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000315600001", new Money(1), CrDr.CREDIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000415600001", new Money(2), CrDr.DEBIT)
                )
        );
        accountOperationService.process(request);
        assertAccountResult(request);

        // 2借1贷
        request = new AccountingRequest();
        request.setRequestNo(randomId());
        request.setEntryDetails(
                List.of(buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000215600001", new Money(1), CrDr.DEBIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000315600001", new Money(1), CrDr.DEBIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000415600001", new Money(2), CrDr.CREDIT)
                )
        );
        accountOperationService.process(request);
        assertAccountResult(request);


        // 2借2贷
        request = new AccountingRequest();
        request.setRequestNo(randomId());
        request.setEntryDetails(
                List.of(buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000215600001", new Money(1), CrDr.DEBIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000315600001", new Money(2), CrDr.DEBIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000415600001", new Money(2), CrDr.CREDIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000515600001", new Money(1), CrDr.CREDIT)
                )
        );
        accountOperationService.process(request);
        assertAccountResult(request);


        // 2借2贷，多套号
        request = new AccountingRequest();
        request.setRequestNo(randomId());
        request.setEntryDetails(
                List.of(buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000215600001", new Money(2), CrDr.DEBIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000315600001", new Money(2), CrDr.CREDIT),
                        buildEntryDetail("2", "200100200110000000415600001", new Money(2), CrDr.DEBIT),
                        buildEntryDetail("2", "200100200110000000515600001", new Money(2), CrDr.CREDIT)
                )
        );
        accountOperationService.process(request);
        assertAccountResult(request);
    }

    @Test
    public void outer2OuterFail() {

        // 1借2贷不平衡
        wrapperBizException(AccountResultCode.CR_DR_NOT_EXISTS, () -> {
            AccountingRequest request = new AccountingRequest();
            request.setRequestNo(randomId());
            request.setEntryDetails(
                    List.of(buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000215600001", new Money(1), CrDr.CREDIT),
                            buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000315600001", new Money(1), CrDr.CREDIT),
                            buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000415600001", new Money(3), CrDr.DEBIT)
                    )
            );
            accountOperationService.process(request);
        });


        // 2借2贷一个不平衡
        wrapperBizException(AccountResultCode.CR_DR_NOT_EXISTS, () -> {
            AccountingRequest request = new AccountingRequest();
            request.setRequestNo(randomId());
            request.setEntryDetails(
                    List.of(buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000215600001", new Money(2), CrDr.DEBIT),
                            buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000315600001", new Money(2), CrDr.CREDIT),
                            buildEntryDetail("2", "200100200110000000415600001", new Money(2), CrDr.DEBIT),
                            buildEntryDetail("2", "200100200110000000515600001", new Money(1), CrDr.CREDIT)
                    )
            );
            accountOperationService.process(request);
        });
    }

    @Test
    public void inner2OuterSuccess() {
        // 1借2贷
        AccountingRequest request = new AccountingRequest();
        request.setRequestNo(randomId());
        request.setEntryDetails(
                List.of(buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000215600001", new Money(1), CrDr.CREDIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000315600001", new Money(1), CrDr.CREDIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "40010010011560001", new Money(2), CrDr.DEBIT)
                )
        );
        accountOperationService.process(request);
        assertAccountResult(request);

        request = new AccountingRequest();
        request.setRequestNo(randomId());
        request.setEntryDetails(
                List.of(buildEntryDetail(DEFAULT_SUITE_NO, "40010010011560001", new Money(2), CrDr.DEBIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000215600001", new Money(1), CrDr.CREDIT),
                        buildEntryDetail(DEFAULT_SUITE_NO, "200100200110000000315600001", new Money(1), CrDr.CREDIT)
                )
        );
        accountOperationService.process(request);
        assertAccountResult(request);
    }
}
