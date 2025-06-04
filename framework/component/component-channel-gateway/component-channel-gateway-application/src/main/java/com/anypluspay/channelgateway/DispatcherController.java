package com.anypluspay.channelgateway;

import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.RequestContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channelgateway.types.RequestResponseClass;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.commons.response.GlobalResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 分发controller
 *
 * @author wxj
 * 2024/9/12
 */
@RestController
@Slf4j
public class DispatcherController {

    @Autowired
    private ApplicationContext applicationContext;

    @ResponseBody
    @PostMapping
    public GatewayResult request(HttpServletRequest request, @RequestParam("bean") String bean,
                                 @RequestParam("channelCode") String channelCode,
                                 @RequestParam("apiTypeCode") String apiTypeCode) {
        try {
            ChannelApiType apiType = EnumUtil.getByCode(ChannelApiType.class, apiTypeCode);
            AssertUtil.notNull(apiType, "apiType不能为null");
            String requestBody = getRequestBody(request);
            AssertUtil.notNull(requestBody, "请求报文不能为空");
            Class<? extends RequestContent> requestClass = RequestResponseClass.getRequestClass(apiType);
            ObjectMapper objectMapper = new ObjectMapper();
            RequestContent content = objectMapper.readValue(requestBody, requestClass);
            return invokeBean(bean, channelCode, apiType, content);
        } catch (Exception e) {
            log.error("处理异常", e);
            return buildExceptionResult();
        }
    }

    private GatewayResult buildExceptionResult() {
        GatewayResult gatewayResult = new GatewayResult();
        gatewayResult.setSuccess(false);
        gatewayResult.setApiCode(GlobalResultCode.FAIL.getCode());
        gatewayResult.setApiMessage(GlobalResultCode.FAIL.getMessage());
        gatewayResult.setReceiveTime(LocalDateTime.now());
        return gatewayResult;
    }

    private String getRequestBody(HttpServletRequest request) {
        try {
            BufferedReader br = request.getReader();
            String str;
            StringBuilder requestBody = new StringBuilder();
            while ((str = br.readLine()) != null) {
                requestBody.append(str);
            }
            return requestBody.toString();
        } catch (IOException e) {
            return "";
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private GatewayResult invokeBean(String bean, String channelCode, ChannelApiType channelApiType, RequestContent content) {
        ChannelGateway channelGateway = applicationContext.getBean(bean, ChannelGateway.class);
        GatewayRequest request = new GatewayRequest();
        request.setChannelCode(channelCode);
        request.setChannelApiType(channelApiType);
        request.setContent(content);
        return channelGateway.call(request);
    }

}
