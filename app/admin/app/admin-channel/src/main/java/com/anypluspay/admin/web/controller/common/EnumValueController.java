package com.anypluspay.admin.web.controller.common;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.anypluspay.admin.model.common.EnumObject;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.channel.InstAbility;
import com.anypluspay.channel.types.channel.MaintainTimeType;
import com.anypluspay.channel.types.enums.CardType;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.channel.types.order.InstProcessOrderStatus;
import com.anypluspay.commons.enums.CodeEnum;
import com.anypluspay.commons.response.ResponseResult;
import jakarta.validation.constraints.NotBlank;
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
 *
 * @author wxj
 * 2024/11/7
 */
@RestController
@RequestMapping("/enum-value")
public class EnumValueController {

    /**
     * 需要输出的的枚举类型
     */
    private static final List<Class<? extends CodeEnum>> codeEnumList = Arrays.asList(
            InstAbility.class,
            ChannelApiType.class,
            RequestType.class,
            CardType.class,
            MaintainTimeType.class,
            InstOrderStatus.class,
            InstProcessOrderStatus.class
    );

    /**
     * 缓存枚举，key为枚举类型类名称
     */
    private static final Map<String, List<EnumObject>> codeEnumCacheMap = new HashMap<>();

    static {
        codeEnumList.forEach(clazz -> codeEnumCacheMap.put(StrUtil.lowerFirst(clazz.getSimpleName()), Arrays.stream(clazz.getEnumConstants())
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
            Assert.isTrue(codeEnumCacheMap.containsKey(t), "不支持的类型");
            enumObjectMap.put(t, codeEnumCacheMap.get(t));
        }
        return ResponseResult.success(enumObjectMap);
    }

}
