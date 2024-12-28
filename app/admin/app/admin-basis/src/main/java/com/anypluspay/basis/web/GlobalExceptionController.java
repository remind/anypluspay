package com.anypluspay.basis.web;

import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.response.GlobalResultCode;
import com.anypluspay.commons.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author wxj
 * 2024/8/26
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseResult<String> exceptionHandler(HttpServletRequest request, Exception e) {
        if (e instanceof BizException) {
            log.error("业务异常", e);
            return ResponseResult.fail(e.getMessage());
        } else if (e instanceof MethodArgumentNotValidException exception) {
            if (exception.getBindingResult().getErrorCount() > 0) {
                List<ObjectError> objectErrorList = exception.getBindingResult().getAllErrors();
                return ResponseResult.fail(objectErrorList.get(0).getDefaultMessage());
            }
        } else if (e instanceof MissingServletRequestParameterException exception) {
            log.error("参数异常,url={},异常信息={}", request.getRequestURI(), e.getMessage(), e);
            return ResponseResult.fail(GlobalResultCode.ILLEGAL_PARAM);
        } else if (e instanceof IllegalArgumentException exception) {
            log.error("参数异常,url={},异常信息={}", request.getRequestURI(), e.getMessage(), e);
            return ResponseResult.fail(exception.getMessage());
        } else {
            log.error("请求异常,url={},异常信息={}", request.getRequestURI(), e.getMessage(), e);
        }
        return ResponseResult.fail();
    }
}