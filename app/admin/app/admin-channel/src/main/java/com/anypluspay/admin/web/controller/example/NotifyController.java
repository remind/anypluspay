package com.anypluspay.admin.web.controller.example;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.facade.NotifyFacade;
import com.anypluspay.channel.facade.result.FundResult;
import com.anypluspay.channel.types.ExtKey;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.commons.lang.utils.ExtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通知处理器
 *
 * @author wxj
 * 2024/12/8
 */
@RestController
public class NotifyController {

    @DubboReference
    private NotifyFacade notifyFacade;

    /**
     * 异步通知
     *
     * @param apiType     接口类型
     * @param channelCode 渠道编码
     * @param request     请求参数
     * @return 结果
     */
    @RequestMapping("/notify/{apiType}/{channelCode}")
    public String notify(@Validated @NotBlank @PathVariable String apiType, @Validated @NotBlank @PathVariable String channelCode, HttpServletRequest request) {
        Map<String, String> requestData = getRequestParams(request);
        FundResult fundResult = notifyFacade.notify(channelCode, EnumUtil.getByCode(ChannelApiType.class, apiType), JSONUtil.toJsonStr(requestData));
        return ExtUtil.getStringValue(ExtKey.NOTIFY_RESPONSE_DATA, fundResult.getResponseExtra());
    }

    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> requestData = new ConcurrentHashMap<>();
        for (String item : request.getParameterMap().keySet()) {
            String value = request.getParameter(item);
            // 过滤value值为空
            if (value == null) {
                continue;
            }
            requestData.put(item, value);
        }
        return requestData;
    }
}
