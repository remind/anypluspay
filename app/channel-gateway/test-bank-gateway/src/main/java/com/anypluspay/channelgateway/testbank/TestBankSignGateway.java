package com.anypluspay.channelgateway.testbank;

import com.anypluspay.channelgateway.AbstractTestBank;
import com.anypluspay.channelgateway.api.sign.SignGateway;
import com.anypluspay.channelgateway.api.sign.SignNormalContent;
import com.anypluspay.channelgateway.api.sign.SignResult;
import com.anypluspay.channelgateway.request.GatewayRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wxj
 * 2024/11/27
 */
@Service
public class TestBankSignGateway extends AbstractTestBank implements SignGateway {

    @Override
    public void sign(GatewayRequest<SignNormalContent> gatewayRequest, SignNormalContent signOrderInfo, SignResult result) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("outTradeNo", signOrderInfo.getInstRequestNo());
        parameters.put("amount", signOrderInfo.getAmount().getAmount().toString());
        parameters.put("subject", signOrderInfo.getGoodsSubject());
        parameters.put("goodsDesc", signOrderInfo.getGoodsDesc());
        parameters.put("notifyUrl", signOrderInfo.getServerNotifyUrl());
        parameters.put("returnUrl", signOrderInfo.getCallbackPageUrl());
        String formHtml = buildForm(url + "/online-bank/pay", parameters);
        result.setInstPageUrl(formHtml);
        result.setSuccess(true);
        result.setApiCode("0000");
    }

    public static String buildForm(String baseUrl, Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form name=\"this_is_form\" method=\"post\" target=\"_blank\" action=\"");
        sb.append(baseUrl);
        sb.append("\">\n");
        sb.append(buildHiddenFields(parameters));

        sb.append("<input type=\"submit\" value=\"立即支付\" style=\"display:none\" >\n");
        sb.append("</form>\n");
        sb.append("<script>document.forms['this_is_form'].submit();</script>");
        return sb.toString();
    }

    private static String buildHiddenFields(Map<String, String> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Set<String> keys = parameters.keySet();
        for (String key : keys) {
            String value = parameters.get(key);
            // 除去参数中的空值
            if (key == null || value == null) {
                continue;
            }
            sb.append(buildHiddenField(key, value));
        }
        return sb.toString();
    }

    private static String buildHiddenField(String key, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("<input type=\"hidden\" name=\"");
        sb.append(key);

        sb.append("\" value=\"");
        //转义双引号
        String a = value.replace("\"", "&quot;");
        sb.append(a).append("\">\n");
        return sb.toString();
    }
}
