package com.anypluspay.component.rpc;

import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * feign调用，对于服务提供方抛出的异常，需要返回给调用方，然后在调用方侧再抛出异常
 *
 * @author wxj
 * 2024/12/25
 */
@Slf4j
@ControllerAdvice
public class FacadeExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResponseResult<?>> handleAllExceptions(Exception ex) {
        String errorCode = "";
        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Internal Server Error";
        if (ex instanceof BizException bizException) {
            errorCode = bizException.getCode();
        } else {
            log.error("系统异常:", ex);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(RpcConstants.ERROR_STATUS_KEY, RpcConstants.ERROR_STATUS_VALUE);
        ResponseResult<?> result = ResponseResult.fail(errorCode, errorMessage);
        return new ResponseEntity<>(result, headers, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
