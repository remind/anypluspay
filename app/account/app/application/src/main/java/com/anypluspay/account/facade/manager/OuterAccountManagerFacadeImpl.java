package com.anypluspay.account.facade.manager;

import com.anypluspay.account.domain.OuterAccount;
import com.anypluspay.account.domain.repository.OuterAccountRepository;
import com.anypluspay.account.domain.service.OuterAccountDomainService;
import com.anypluspay.account.facade.manager.builder.OuterAccountBuilder;
import com.anypluspay.account.facade.manager.convertor.OuterAccountConvertor;
import com.anypluspay.account.facade.manager.request.OuterAccountRequest;
import com.anypluspay.account.facade.manager.response.OuterAccountResponse;
import com.anypluspay.account.types.enums.DenyStatus;
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
public class OuterAccountManagerFacadeImpl implements OuterAccountManagerFacade {

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

    @Override
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

    @Override
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

    @Override
    public void update(OuterAccountRequest request) {
        OuterAccount account = outerAccountRepository.load(request.getAccountNo());
        AssertUtil.notNull(account, "账户不存在");
        account.setAccountName(request.getAccountName());
        outerAccountRepository.reStore(account);
    }

    @Override
    public void changeDenyStatus(String accountNo, String denyStatusCode) {
        transactionTemplate.executeWithoutResult(status -> {
            OuterAccount outerAccount = outerAccountRepository.lock(accountNo);
            DenyStatus denyStatus = EnumUtil.getByCode(DenyStatus.class, denyStatusCode);
            AssertUtil.notNull(denyStatus, "状态不存在");
            outerAccountDomainService.changeDenyStatus(outerAccount, denyStatus);
        });
    }

    @Override
    public OuterAccountResponse detail(String accountNo) {
        OuterAccount outerAccount = outerAccountRepository.load(accountNo);
        return outerAccountConvertor.toResponse(outerAccount);
    }

    @Override
    public OuterAccountResponse queryByMemberIdAndAccountType(String memberId, String accountType) {
        OuterAccount outerAccount = outerAccountRepository.queryByMemberIdAndAccountType(memberId, accountType);
        return outerAccountConvertor.toResponse(outerAccount);
    }

    @Override
    public List<OuterAccountResponse> queryByMemberId(String memberId) {
        List<OuterAccount> outerAccounts = outerAccountRepository.queryByMemberId(memberId);
        return outerAccounts.stream().map(outerAccount -> outerAccountConvertor.toResponse(outerAccount)).toList();
    }
}
