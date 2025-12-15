package com.anypluspay.anypay.application.account;

import com.anypluspay.anypay.domain.account.OuterAccount;
import com.anypluspay.anypay.domain.account.repository.OuterAccountRepository;
import com.anypluspay.anypay.domain.account.service.OuterAccountDomainService;
import com.anypluspay.anypay.application.account.builder.OuterAccountBuilder;
import com.anypluspay.anypay.application.account.convertor.OuterAccountConvertor;
import com.anypluspay.anypay.application.account.request.OuterAccountRequest;
import com.anypluspay.anypay.application.account.response.OuterAccountResponse;
import com.anypluspay.anypay.types.account.DenyStatus;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.lang.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 外部户管理
 *
 * @author wxj
 * 2025/1/7
 */
@RestController
@Slf4j
public class OuterAccountManagerFacadeImpl {

    @Autowired
    private OuterAccountBuilder outerAccountBuilder;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private OuterAccountRepository outerAccountRepository;

    @Autowired
    private OuterAccountConvertor outerAccountConvertor;

    @Autowired
    private OuterAccountDomainService outerAccountDomainService;

    public OuterAccountResponse create(OuterAccountRequest request) {
        try {
            OuterAccount account = outerAccountBuilder.build(request);
            String accountNo = transactionTemplate.execute(status -> outerAccountRepository.store(account));
            return detail(accountNo);
        } catch (Exception e) {
            log.error("外部户开户失败,memberId=" + request.getMemberId(), e);
            throw new BizException(e);
        }
    }

    public List<OuterAccountResponse> create(List<OuterAccountRequest> requests) {
        List<OuterAccount> outerAccounts = outerAccountBuilder.build(requests);
        List<OuterAccountResponse> responses = new ArrayList<>();
        transactionTemplate.executeWithoutResult(status -> {
            for (OuterAccount outerAccount : outerAccounts) {
                String accountNo = outerAccountRepository.store(outerAccount);
                responses.add(detail(accountNo));
            }
        });
        return responses;
    }

    public void update(OuterAccountRequest request) {
        OuterAccount account = outerAccountRepository.load(request.getAccountNo());
        AssertUtil.notNull(account, "账户不存在");
        account.setAccountName(request.getAccountName());
        outerAccountRepository.reStore(account);
    }

    public void changeDenyStatus(String accountNo, String denyStatusCode) {
        transactionTemplate.executeWithoutResult(status -> {
            OuterAccount outerAccount = outerAccountRepository.lock(accountNo);
            DenyStatus denyStatus = EnumUtil.getByCode(DenyStatus.class, denyStatusCode);
            AssertUtil.notNull(denyStatus, "状态不存在");
            outerAccountDomainService.changeDenyStatus(outerAccount, denyStatus);
        });
    }

    public OuterAccountResponse detail(String accountNo) {
        OuterAccount outerAccount = outerAccountRepository.load(accountNo);
        return outerAccountConvertor.toResponse(outerAccount);
    }

    public OuterAccountResponse queryByMemberIdAndAccountType(String memberId, String accountType) {
        OuterAccount outerAccount = outerAccountRepository.queryByMemberIdAndAccountType(memberId, accountType);
        return outerAccountConvertor.toResponse(outerAccount);
    }

    public List<OuterAccountResponse> queryByMemberId(String memberId) {
        List<OuterAccount> outerAccounts = outerAccountRepository.queryByMemberId(memberId);
        return outerAccounts.stream().map(outerAccount -> outerAccountConvertor.toResponse(outerAccount)).toList();
    }
}
