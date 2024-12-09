package com.anypluspay.channel;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxj
 * 2024/11/28
 */
public class MainTest {

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>();
        params.put("outRequestNo", "123");
        params.put("origOrderId", "123");
        params.put("amount", "123");
        params.put("reason", "123");
        params.put("notifyUrl", "123");
        params.put("notify", "true");
        String  url = "http://localhost:9080";
        WebClient webClient = WebClient.builder().baseUrl(url).build();
        webClient.post().uri("/online-bank/refund")
                .bodyValue(params)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
