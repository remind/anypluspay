package com.anypluspay.account.facade.accounting;


import com.anypluspay.account.domain.accounting.AccountTitle;
import com.anypluspay.account.domain.accounting.service.AccountTitleDomainService;
import com.anypluspay.account.domain.repository.AccountTitleRepository;
import com.anypluspay.account.facade.accounting.convertor.AccountTitleConvertor;
import com.anypluspay.account.facade.accounting.dto.AccountTitleAddRequest;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxj
 * 2023/12/16
 */
@Slf4j
public class AccountTitleManagerFacadeImpl implements AccountTitleManagerFacade {

    @Autowired
    private AccountTitleRepository accountTitleRepository;

    @Autowired
    private AccountTitleConvertor accountTitleConvertor;

    @Autowired
    private AccountTitleDomainService accountTitleDomainService;

    @Override
    public ResponseResult<String> createAccountTitle(AccountTitleAddRequest request) {
        String parentTitleCode = request.getParentTitleCode();
        try {
            AccountTitle parentAccountTitle = null;
            if (StringUtils.isNotBlank(parentTitleCode)) {
                parentAccountTitle = accountTitleRepository.load(parentTitleCode);
                AssertUtil.notNull(parentAccountTitle, "父科目为空");
            }
            AccountTitle accountTitle = accountTitleConvertor.toEntity(request);
            accountTitleDomainService.setAccountTitleLevel(accountTitle, parentAccountTitle);
            accountTitleRepository.store(accountTitle);
        } catch (BizException e) {
            log.info("新增科目异常,titleCode={}", request.getTitleCode());
            return ResponseResult.fail(e.getMessage());
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<String> updateAccountTitle(AccountTitleAddRequest request) {
        return null;
    }
}
