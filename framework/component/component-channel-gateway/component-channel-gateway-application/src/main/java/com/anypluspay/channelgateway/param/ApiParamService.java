package com.anypluspay.channelgateway.param;

import com.anypluspay.channel.facade.manager.FundChannelManagerFacade;
import com.anypluspay.channel.facade.manager.response.ChannelApiParamResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 渠道api参数
 *
 * @author wxj
 * 2025/6/4
 */
@Service
@Slf4j
public class ApiParamService {

    @Autowired
    private FundChannelManagerFacade fundChannelManagerFacade;

    /**
     * 监听器，用于监听参数刷新
     */
    private final List<ApiParamRefreshListener> listeners = new ArrayList<>();

    /**
     * 参数本地缓存
     */
    private final Map<String, String> apiParams = new ConcurrentHashMap<>();

    /**
     * 获取api参数
     *
     * @param id 参数ID
     * @return
     */
    public String getApiParam(String id) {
        if (!apiParams.containsKey(id)) {
            ChannelApiParamResponse response = fundChannelManagerFacade.getChannelApiParam(id);
            if (response != null) {
                apiParams.put(id, response.getParam());
            }
        }
        return apiParams.get(id);
    }

    /**
     * 添加监听器
     *
     * @param listener
     */
    public void addListener(ApiParamRefreshListener listener) {
        listeners.add(listener);
    }

    /**
     * 刷新api参数
     *
     * @param id    参数ID
     * @param param 参数字符串
     */
    public void refreshApiParam(String id, String param) {
        apiParams.put(id, param);
        listeners.forEach(apiParamRefreshListener -> apiParamRefreshListener.onRefresh(id, param));
        log.info("刷新api参数: {}", id);
    }

}
