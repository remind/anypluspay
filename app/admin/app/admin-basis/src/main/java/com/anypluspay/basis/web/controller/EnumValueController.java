package com.anypluspay.basis.web.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.anypluspay.basis.model.EnumObject;
import com.anypluspay.commons.enums.CodeEnum;
import com.anypluspay.commons.response.ResponseResult;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 枚举值
 * @author wxj
 * 2024/12/30
 */
@RestController
@RequestMapping("/enum-value")
public class EnumValueController {

    private Map<String, List<EnumObject>> codeEnumCacheMap = null;

    @Autowired
    private List<Class<? extends CodeEnum>> codeEnumList;

    /**
     * 获取CodeName型的值列表
     *
     * @param type 类型,多个用“,”分隔
     * @return 枚举值列表
     */
    @GetMapping("/code-name")
    public ResponseResult<Map<String, List<EnumObject>>> getCodeNameValues(@RequestParam @NotBlank String type) {
        init();
        String[] types = type.split(",");
        Map<String, List<EnumObject>> enumObjectMap = new HashMap<>();
        for (String t : types) {
            Assert.isTrue(codeEnumCacheMap.containsKey(t), "不支持的类型");
            enumObjectMap.put(t, codeEnumCacheMap.get(t));
        }
        return ResponseResult.success(enumObjectMap);
    }

    private void init() {
        if (codeEnumCacheMap == null) {
            codeEnumCacheMap = new HashMap<>();
            codeEnumList.forEach(clazz -> codeEnumCacheMap.put(StrUtil.lowerFirst(clazz.getSimpleName()), Arrays.stream(clazz.getEnumConstants())
                    .map(codeEnum -> new EnumObject(codeEnum.getCode(), codeEnum.getDisplayName()))
                    .toList()));
        }
    }

}

