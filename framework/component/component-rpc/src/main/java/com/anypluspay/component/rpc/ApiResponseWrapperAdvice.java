package com.anypluspay.component.rpc;

import com.anypluspay.commons.response.ResponseResult;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author wxj
 * 2024/12/26
 */
//@ControllerAdvice
//@Order(2)
//public class ApiResponseWrapperAdvice implements ResponseBodyAdvice<Object> {
//    @Override
//    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//        if (body instanceof ResponseResult) {
//            return body;
//        }
//        // 加个标识，标明是包装类型，在调用端可以根据此做判断
////        response.getHeaders().add("x-wrapper-status","1");
//        return ResponseResult.success(body);
//    }
//}
