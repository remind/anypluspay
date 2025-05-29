package com.anypluspay.admin.auth.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.anypluspay.admin.auth.SysUserStatus;
import com.anypluspay.admin.auth.request.AuthLoginInfo;
import com.anypluspay.admin.auth.request.AuthLoginRequest;
import com.anypluspay.admin.auth.response.AuthLoginResponse;
import com.anypluspay.admin.infra.persistence.dataobject.SysUserDO;
import com.anypluspay.admin.infra.persistence.mapper.SysUserMapper;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.response.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证处理
 *
 * @author wxj
 * 2024/11/3
 */
@RestController
public class AuthController {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 登录
     *
     * @param request 请求对象
     * @return 登录结果
     */
    @PostMapping("/auth/login")
    public ResponseResult<AuthLoginResponse> login(@RequestBody AuthLoginRequest request) {
        LambdaQueryWrapper<SysUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDO::getUsername, request.getUsername());
        SysUserDO sysUserDO = sysUserMapper.selectOne(queryWrapper);
        if (sysUserDO != null) {
            AssertUtil.isFalse(sysUserDO.getStatus().equals(SysUserStatus.DISABLE.getCode()), "用户被禁用");
            if (sysUserDO.getPassword().equals(SaSecureUtil.md5BySalt(request.getPassword(), sysUserDO.getSalt()))) {
                StpUtil.login(sysUserDO.getId());
                return ResponseResult.success(new AuthLoginResponse(StpUtil.getTokenValue()));
            }
        }
        throw new BizException("用户名或密码错误");
    }

    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     */
    @GetMapping("/user/info")
    public ResponseResult<AuthLoginInfo> loginUser() {
        SysUserDO sysUserDO = sysUserMapper.selectById(StpUtil.getLoginIdAsLong());
        AssertUtil.notNull(sysUserDO, "用户不存在");
        AuthLoginInfo loginInfo = new AuthLoginInfo();
        loginInfo.setRealName(sysUserDO.getNickname());
        loginInfo.setUsername(sysUserDO.getUsername());
        return ResponseResult.success(loginInfo);
    }

    /**
     * 登出
     *
     * @return 登出结果
     */
    @PostMapping("/auth/logout")
    public ResponseResult<String> logout() {
        StpUtil.logout();
        return ResponseResult.success();
    }

}
