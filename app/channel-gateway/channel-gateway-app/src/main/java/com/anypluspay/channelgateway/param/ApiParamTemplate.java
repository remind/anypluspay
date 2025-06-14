package com.anypluspay.channelgateway.param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * api参数获取模板方法
 *
 * @author wxj
 * 2025/6/5
 */
@Service
public class ApiParamTemplate {

    @Autowired
    private ApiParamService apiParamService;

    private final Map<String, Map<String,  Object>> paramMap = new ConcurrentHashMap<>();

    /**
     * 加载配置，会有缓存
     *
     * @param paramId
     * @param cacheKey
     * @param function
     * @param <T>
     * @return
     */
    public <T> T load(String paramId, String cacheKey, Function<String, T> function) {
        if (!paramMap.containsKey(paramId) || !paramMap.get(paramId).containsKey(cacheKey)) {
            if (!paramMap.containsKey(paramId)) {
                paramMap.put(paramId, new ConcurrentHashMap<>());
            }
            String param = apiParamService.getApiParam(paramId);
            paramMap.get(paramId).put(cacheKey, function.apply(param));
        }
        return (T) paramMap.get(paramId).get(cacheKey);
    }

    public ApiParamTemplate(ApiParamService apiParamService) {
        this.apiParamService = apiParamService;
        apiParamService.addListener(new DefaultParamRefresh(paramMap));
    }

    private static class DefaultParamRefresh implements ApiParamRefreshListener {

        private final Map<String, Map<String,  Object>> paramMap;

        public DefaultParamRefresh(Map<String, Map<String,  Object>> paramMap) {
            this.paramMap = paramMap;
        }

        @Override
        public void onRefresh(String id, String param) {
            paramMap.remove(id);
        }
    }
}
