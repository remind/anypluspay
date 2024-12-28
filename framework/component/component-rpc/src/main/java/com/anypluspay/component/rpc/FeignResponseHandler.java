//package com.anypluspay.component.rpc;
//
//import cn.hutool.core.lang.ParameterizedTypeImpl;
//import com.anypluspay.commons.exceptions.BizException;
//import com.anypluspay.commons.response.ResponseResult;
//import feign.FeignException;
//import feign.Response;
//import feign.codec.Decoder;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.Collection;
//import java.util.Map;
//
///**
// * @author wxj
// * 2024/12/25
// */
//@Slf4j
//public class FeignResponseHandler implements Decoder {
//
//    private final Decoder decoder;
//
//    @Autowired
//    public FeignResponseHandler(Decoder decoder) {
//        this.decoder = decoder;
//    }
//
//    @Override
//    public Object decode(Response response, Type type) throws IOException, FeignException {
//        String exceptionStatus = getValue(response.headers(), "x-exception-status");
//        if ("1".equals(exceptionStatus)) {
//            ResponseResult<?> result = (ResponseResult<?>) this.decoder.decode(response, ResponseResult.class);
//            throw new BizException(result.getCode(), result.getMessage());
//        } else {
//            ParameterizedTypeImpl resultType = new ParameterizedTypeImpl(new Type[]{type}, ResponseResult.class, null);
//            ResponseResult<?> result = null;
//            try {
//                result = (ResponseResult<?>) this.decoder.decode(response, resultType);
//            } catch (Exception e) {
//                log.error("decode:", e);
//                throw new RuntimeException(e);
//            }
//            return result.getData();
//        }
//    }
//
//    private String getValue(Map<String, Collection<String>> headers, String key) {
//        return headers.get(key) != null ? headers.get(key).iterator().next() : null;
//    }
//}
