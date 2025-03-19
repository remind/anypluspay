package com.anypluspay.component.rpc;

import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.response.ResponseResult;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author wxj
 * 2024/12/26
 */
public class FacadeErrorResponseDecoder implements ErrorDecoder {

    private final Decoder decoder;

    @Autowired
    public FacadeErrorResponseDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        String exceptionStatus = getValue(response.headers(), RpcConstants.ERROR_STATUS_KEY);
        if (RpcConstants.ERROR_STATUS_VALUE.equals(exceptionStatus) && response.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            try {
                ResponseResult<?> result = (ResponseResult<?>) this.decoder.decode(response, ResponseResult.class);
                return new BizException(result.getCode(), result.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new ErrorDecoder.Default().decode(methodKey, response);
    }

    private String getValue(Map<String, Collection<String>> headers, String key) {
        return headers.get(key) != null ? headers.get(key).iterator().next() : null;
    }
}
