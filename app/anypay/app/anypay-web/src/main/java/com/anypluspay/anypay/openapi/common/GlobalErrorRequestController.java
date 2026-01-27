package com.anypluspay.anypay.openapi.common;

import com.anypluspay.commons.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局错误请求处理
 * @author wxj
 * 2023/12/14
 */
@RestController
public class GlobalErrorRequestController implements ErrorController {

    @RequestMapping("/error")
    public ResponseResult<String> error(HttpServletRequest request) {
        return ResponseResult.fail("404");
    }

}
