package com.anypluspay.admin.account.controller;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.account.facade.manager.AccountManagerFacade;
import com.anypluspay.account.facade.manager.dto.InnerAccountRequest;
import com.anypluspay.account.infra.persistence.dataobject.InnerAccountDO;
import com.anypluspay.account.infra.persistence.mapper.InnerAccountMapper;
import com.anypluspay.admin.account.convertor.InnerAccountConvertor;
import com.anypluspay.admin.account.dto.InnerAccountDto;
import com.anypluspay.admin.account.query.InnerAccountQuery;
import com.anypluspay.basis.web.controller.AbstractController;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.commons.validator.UpdateValidate;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 内部账户
 *
 * @author wxj
 * 2025/1/5
 */
@RestController
@RequestMapping("/account/inner-account")
public class InnerAccountController extends AbstractController {

    @Autowired
    private InnerAccountConvertor convertor;

    @Autowired
    private InnerAccountMapper dalMapper;

    @Autowired
    private AccountManagerFacade accountManagerFacade;

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<InnerAccountDto>> list(InnerAccountQuery query) {
        LambdaQueryWrapper<InnerAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getAccountNo())) {
            queryWrapper.eq(InnerAccountDO::getAccountNo, query.getAccountNo());
        } else {
            if (StrUtil.isNotBlank(query.getAccountName())) {
                queryWrapper.like(InnerAccountDO::getAccountName, query.getAccountName());
            }
            if (StrUtil.isNotBlank(query.getTitleCode())) {
                queryWrapper.eq(InnerAccountDO::getTitleCode, query.getTitleCode());
            }
        }
        queryWrapper.orderByAsc(InnerAccountDO::getGmtCreate);
        return ResponseResult.success(convertor.toDto(dalMapper.selectPage(getIPage(query), queryWrapper)));
    }

    /**
     * 根据主键查询详情
     *
     * @param accountNo 账户号
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResponseResult<InnerAccountDto> detail(@RequestParam String accountNo) {
        return ResponseResult.success(convertor.toDto(dalMapper.selectById(accountNo)));
    }

    /**
     * 新增
     *
     * @param request 新增请求对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody @Validated(UpdateValidate.class) InnerAccountRequest request) {
        accountManagerFacade.createInnerAccount(request);
        return ResponseResult.success();
    }

    /**
     * 修改
     *
     * @param request 修改请求对象
     * @return 修改结果
     */
    @PostMapping("/update")
    public ResponseResult<String> update(@RequestBody @Validated(UpdateValidate.class) InnerAccountRequest request) {
        accountManagerFacade.updateInnerAccount(request);
        return ResponseResult.success();
    }

    /**
     * 删除
     *
     * @param accountNo 账户号
     * @return 删除结果
     */
    @GetMapping("/delete")
    public ResponseResult<String> delete(@RequestParam String accountNo) {
        accountManagerFacade.deleteInnerAccount(accountNo);
        return ResponseResult.success();
    }
}
