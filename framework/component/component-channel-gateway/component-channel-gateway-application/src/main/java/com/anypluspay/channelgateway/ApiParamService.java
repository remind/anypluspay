package com.anypluspay.channelgateway;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.facade.ChannelApiParamFacade;
import com.anypluspay.channel.facade.response.ChannelApiParamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 渠道api参数
 *
 * @author wxj
 * 2025/6/4
 */
@Service
public class ApiParamService {

    @Autowired
    private ChannelApiParamFacade channelApiParamFacade;

    private final Map<String, String> apiParams = new ConcurrentHashMap<>();

    /**
     * 获取api参数
     *
     * @param id    参数ID
     * @param clazz 参数对象类型
     * @param <T>
     * @return
     */
    public <T> T getApiParam(String id, Class<T> clazz) {
        if (!apiParams.containsKey(id)) {
            ChannelApiParamResponse response = channelApiParamFacade.getChannelApiParam(id);
            if (response != null) {
                apiParams.put(id, response.getParam());
            }
        }
        return JSONUtil.toBean(apiParams.get(id), clazz);
    }

    /**
     * 刷新api参数
     *
     * @param id    参数ID
     * @param param 参数字符串
     */
    public void refreshApiParam(String id, String param) {
        apiParams.put(id, param);
    }

}
