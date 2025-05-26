package com.anypluspay.admin.auth.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.RandomUtil;
import com.anypluspay.admin.account.model.dto.AccountTitleDto;
import com.anypluspay.admin.auth.SysUserStatus;
import com.anypluspay.admin.auth.convertor.SysUserConvertor;
import com.anypluspay.admin.auth.query.SysUserQuery;
import com.anypluspay.admin.auth.request.SysUserRequest;
import com.anypluspay.admin.auth.request.SysUserUpdatePasswordRequest;
import com.anypluspay.admin.auth.response.SysUserResponse;
import com.anypluspay.admin.core.controller.AbstractController;
import com.anypluspay.admin.infra.persistence.dataobject.SysUserDO;
import com.anypluspay.admin.infra.persistence.mapper.SysUserMapper;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户管理
 *
 * @author wxj
 * 2025/5/26
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserConvertor convertor;

    @Autowired
    private SysUserMapper dalMapper;

    /**
     * 列表
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<SysUserResponse>> list(SysUserQuery query) {
        LambdaQueryWrapper<SysUserDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getNickname())) {
            queryWrapper.like(SysUserDO::getNickname, query.getNickname());
        }

        if (StringUtils.isNotBlank(query.getUsername())) {
            queryWrapper.like(SysUserDO::getUsername, query.getUsername());
        }

        queryWrapper.orderByDesc(SysUserDO::getGmtCreate);
        IPage<SysUserDO> page = getIPage(query);
        return ResponseResult.success(convertor.toDto(dalMapper.selectPage(page, queryWrapper)));
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResponseResult<SysUserResponse> detail(@RequestParam Long id) {
        return ResponseResult.success(convertor.toDto(dalMapper.selectById(id)));
    }

    /**
     * 新增
     *
     * @param request 新增请求对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody @Validated(AddValidate.class) SysUserRequest request) {
        SysUserDO sysUserDO = convertor.requestToDo(request);
        fillPassword(sysUserDO, request.getPassword());
        if (dalMapper.insert(sysUserDO) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("新增失败");
        }
    }

    /**
     * 修改
     *
     * @param request 修改请求对象
     * @return 修改结果
     */
    @PostMapping("/update")
    public ResponseResult<String> update(@RequestBody @Validated(UpdateValidate.class) SysUserRequest request) {
        SysUserDO sysUserDO = dalMapper.selectById(request.getId());
        AssertUtil.notNull(sysUserDO, "用户不存在");
        AssertUtil.notNull(EnumUtil.getByCode(SysUserStatus.class, request.getStatus()), "状态值错误");
        sysUserDO.setNickname(request.getNickname());
        sysUserDO.setStatus(request.getStatus());
        if (dalMapper.updateById(sysUserDO) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("修改失败");
        }
    }

    /**
     * 修改密码
     *
     * @param request 修改请求对象
     * @return 修改结果
     */
    @PostMapping("/update-password")
    public ResponseResult<String> updatePassword(@RequestBody @Validated SysUserUpdatePasswordRequest request) {
        SysUserDO sysUserDO = dalMapper.selectById(request.getId());
        AssertUtil.notNull(sysUserDO, "用户不存在");
        fillPassword(sysUserDO, request.getPassword());
        if (dalMapper.updateById(sysUserDO) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param id 主键ID
     * @return 删除结果
     */
    @GetMapping("/delete")
    public ResponseResult<String> delete(@RequestParam Long id) {
        if (dalMapper.deleteById(id) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("删除失败");
        }
    }

    /**
     * 填充密码
     *
     * @param sysUserDO 系统用户对象
     * @param password  密码
     */
    private void fillPassword(SysUserDO sysUserDO, String password) {
        sysUserDO.setSalt(RandomUtil.randomString(6));
        sysUserDO.setPassword(SaSecureUtil.md5BySalt(password, sysUserDO.getSalt()));
    }

}
