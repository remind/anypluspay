package com.anypluspay.admin.basis.controller;

import cn.hutool.core.lang.UUID;
import com.anypluspay.commons.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxj
 * 2024/11/3
 */
@RestController
public class AuthController {

    @PostMapping("/auth/login")
    public ResponseResult login() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", UUID.fastUUID().toString(true));
        map.put("roles", List.of("super"));
        map.put("username", "admin");
        return ResponseResult.success(map);
    }

    @GetMapping("/user/info")
    public ResponseResult loginUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", List.of("super"));
        map.put("username", "admin");
        return ResponseResult.success(map);
    }

    @GetMapping("/auth/codes")
    public ResponseResult authCodes() {
        return ResponseResult.success(new String[] {"super"});
    }

}
