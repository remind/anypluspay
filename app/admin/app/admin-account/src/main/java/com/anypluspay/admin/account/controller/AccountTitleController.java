package com.anypluspay.admin.account.controller;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.account.facade.manager.AccountTitleManagerFacade;
import com.anypluspay.account.facade.manager.request.AccountTitleRequest;
import com.anypluspay.account.infra.persistence.dataobject.AccountTitleDO;
import com.anypluspay.account.infra.persistence.mapper.AccountTitleMapper;
import com.anypluspay.admin.account.convertor.AccountTitleConvertor;
import com.anypluspay.admin.account.dto.AccountTitleDto;
import com.anypluspay.admin.account.query.AccountTitleQuery;
import com.anypluspay.basis.web.controller.AbstractController;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 科目
 *
 * @author wxj
 * 2024/12/24
 */
@RestController
@RequestMapping("/account/account-title")
public class AccountTitleController extends AbstractController {

    @Autowired
    private AccountTitleMapper dalMapper;

    @Autowired
    private AccountTitleConvertor convertor;

    @Autowired
    private AccountTitleManagerFacade accountTitleManagerFacade;

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<AccountTitleDto>> list(AccountTitleQuery query) {
        LambdaQueryWrapper<AccountTitleDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getCode())) {
            queryWrapper.like(AccountTitleDO::getCode, query.getCode());
        }
        if (StrUtil.isNotBlank(query.getName())) {
            queryWrapper.like(AccountTitleDO::getName, query.getName());
        }
        if (query.getEnable() != null) {
            queryWrapper.eq(AccountTitleDO::getEnable, query.getEnable());
        }
        queryWrapper.orderByAsc(AccountTitleDO::getCode).orderByAsc(AccountTitleDO::getTier);
        return ResponseResult.success(convertor.toDto(dalMapper.selectPage(getIPage(query), queryWrapper)));
    }

    /**
     * 根据主键查询详情
     *
     * @param code 主键
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResponseResult<AccountTitleDto> detail(@RequestParam String code) {
        return ResponseResult.success(convertor.toDto(dalMapper.selectById(code)));
    }

    /**
     * 新增
     *
     * @param request 新增请求对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody @Validated(AddValidate.class) AccountTitleRequest request) {
        accountTitleManagerFacade.create(request);
        return ResponseResult.success();
    }

    /**
     * 修改
     *
     * @param request 修改请求对象
     * @return 修改结果
     */
    @PostMapping("/update")
    public ResponseResult<String> update(@RequestBody @Validated(UpdateValidate.class) AccountTitleRequest request) {
        accountTitleManagerFacade.update(request);
        return ResponseResult.success();
    }

    /**
     * 删除
     *
     * @param code 主键ID
     * @return 删除结果
     */
    @GetMapping("/delete")
    public ResponseResult<String> delete(@RequestParam String code) {
        accountTitleManagerFacade.delete(code);
        return ResponseResult.success();
    }
}
