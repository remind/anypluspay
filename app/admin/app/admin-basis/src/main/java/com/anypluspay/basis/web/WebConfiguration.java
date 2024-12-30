package com.anypluspay.basis.web;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.basis.model.EnumObject;
import com.anypluspay.commons.enums.CodeEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.*;

/**
 * @author wxj
 * 2024/12/30
 */
@Configuration
public class WebConfiguration {

    /**
     * 需要输出的的枚举类型
     */
    @Bean
    public List<Class<? extends CodeEnum>> codeEnumList() {
        return new ArrayList<>();
    }

    /**
     * 缓存枚举，key为枚举类型类名称
     */
    @Bean
    @Lazy
    public Map<String, List<EnumObject>> codeEnumCacheMap(List<Class<? extends CodeEnum>> codeEnumList) {
        Map<String, List<EnumObject>> codeEnumCacheMap = new HashMap<>();
        codeEnumList.forEach(clazz -> codeEnumCacheMap.put(StrUtil.lowerFirst(clazz.getSimpleName()), Arrays.stream(clazz.getEnumConstants())
                .map(codeEnum -> new EnumObject(codeEnum.getCode(), codeEnum.getDisplayName()))
                .toList()));
        return codeEnumCacheMap;
    }
}
