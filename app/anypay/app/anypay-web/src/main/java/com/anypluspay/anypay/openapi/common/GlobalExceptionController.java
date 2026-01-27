package com.anypluspay.anypay.openapi.common;

import cn.dev33.satoken.exception.NotLoginException;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.response.GlobalResultCode;
import com.anypluspay.commons.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 全局异常处理
 * @author wxj
 * 2024/8/26
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<ResponseResult<String>> exceptionHandler(HttpServletRequest request, Exception e) {
        ResponseResult<String> result = null;
        HttpStatus status = HttpStatus.OK;
        if (e instanceof BizException) {
            log.error("业务异常", e);
            result = ResponseResult.fail(e.getMessage());
        } else if (e instanceof MethodArgumentNotValidException exception) {
            if (exception.getBindingResult().getErrorCount() > 0) {
                List<ObjectError> objectErrorList = exception.getBindingResult().getAllErrors();
                result = ResponseResult.fail(objectErrorList.get(0).getDefaultMessage());
            }
        } else if (e instanceof MissingServletRequestParameterException) {
            log.error("参数异常,url={},异常信息={}", request.getRequestURI(), e.getMessage(), e);
            result = ResponseResult.fail(GlobalResultCode.ILLEGAL_PARAM);
        } else if (e instanceof IllegalArgumentException exception) {
            log.error("参数异常,url={},异常信息={}", request.getRequestURI(), e.getMessage(), e);
            result = ResponseResult.fail(exception.getMessage());
        } else if (e instanceof NotLoginException) {
            result = ResponseResult.fail("请先登录");
            status = HttpStatus.UNAUTHORIZED;
        } else {
            log.error("请求异常,url={},异常信息={}", request.getRequestURI(), e.getMessage(), e);
        }
        if (result == null) {
            result = ResponseResult.fail();
        }
        return new ResponseEntity<>(result, status);
    }
}