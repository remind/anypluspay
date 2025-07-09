package com.anypluspay.pgw.wallet.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.anypluspay.commons.enums.CodeEnum;
import com.anypluspay.commons.enums.EnumObject;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.payment.types.paymethod.BankCode;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author wxj
 * 2025/7/9
 */
@RestController
@RequestMapping("/enum-value")
public class EnumValueController {

    private static final Map<String, List<EnumObject>> CODE_ENUM_CACHE_MAP = new HashMap<>();

    static {
        final List<Class<? extends CodeEnum>> CODE_ENUM_LIST = new ArrayList<>();

        // 支付相关
        CODE_ENUM_LIST.add(BankCode.class);

        CODE_ENUM_LIST.forEach(clazz -> CODE_ENUM_CACHE_MAP.put(StrUtil.lowerFirst(clazz.getSimpleName()), Arrays.stream(clazz.getEnumConstants())
                .map(codeEnum -> new EnumObject(codeEnum.getCode(), codeEnum.getDisplayName()))
                .toList()));
    }

    /**
     * 获取CodeName型的值列表
     *
     * @param type 类型,多个用“,”分隔
     * @return 枚举值列表
     */
    @GetMapping("/code-name")
    public ResponseResult<Map<String, List<EnumObject>>> getCodeNameValues(@RequestParam @NotBlank String type) {
        String[] types = type.split(",");
        Map<String, List<EnumObject>> enumObjectMap = new HashMap<>();
        for (String t : types) {
            Assert.isTrue(CODE_ENUM_CACHE_MAP.containsKey(t), "不支持的类型");
            enumObjectMap.put(t, CODE_ENUM_CACHE_MAP.get(t));
        }
        return ResponseResult.success(enumObjectMap);
    }
}
