package com.anypluspay.channelgateway.wxpay;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channelgateway.ChannelGateway;
import com.anypluspay.channelgateway.request.GatewayRequest;
import com.anypluspay.channelgateway.request.RequestContent;
import com.anypluspay.channelgateway.result.GatewayResult;
import com.anypluspay.channelgateway.result.GatewayResultCode;
import com.anypluspay.channelgateway.types.RequestResponseClass;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.commons.response.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wxj
 * 2024/9/12
 */
@RestController
@RequestMapping("/wxpay")
public class DispatcherController {

    @Autowired
    private List<ChannelGateway> channelGateways;

    @Autowired
    private WxPayConfig wxPayConfig;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @ResponseBody
    @PostMapping
    public GatewayResult request(HttpServletRequest request, @RequestParam("channelCode") String channelCode, @RequestParam("apiTypeCode") String apiTypeCode) {
        ChannelApiType apiType = EnumUtil.getByCode(ChannelApiType.class, apiTypeCode);
        AssertUtil.notNull(apiType, "apiType不能为null");
        String requestBody = getRequestBody(request);
        AssertUtil.notNull(requestBody, "请求报文不能为空");
        Class<? extends RequestContent> requestClass = RequestResponseClass.getRequestClass(apiType);
        RequestContent content = JSONUtil.toBean(requestBody, requestClass);
        GatewayRequest gatewayRequest = new GatewayRequest();
        gatewayRequest.setChannelCode(channelCode);
        gatewayRequest.setChannelApiType(apiType);
        gatewayRequest.setContent(content);
//        for (ChannelGateway channelGateway : channelGateways) {
//            if (channelGateway.support(channelCode, apiType)) {
//                return channelGateway.call(gatewayRequest);
//            }
//        }
        return buildResult(GatewayResultCode.NOT_SUPPORT);
    }

    private GatewayResult buildResult(ResultCode resultCode) {
        GatewayResult gatewayResult = new GatewayResult();
        gatewayResult.setSuccess(false);
        gatewayResult.setApiCode(resultCode.getCode());
        gatewayResult.setApiMessage(resultCode.getMessage());
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

}
