package com.anypluspay.admin.basis.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.anypluspay.account.types.accounting.AccountTitleScope;
import com.anypluspay.account.types.accounting.AccountTitleType;
import com.anypluspay.account.types.enums.BalanceDirection;
import com.anypluspay.account.types.enums.DenyStatus;
import com.anypluspay.admin.auth.SysUserStatus;
import com.anypluspay.admin.basis.model.EnumObject;
import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.channel.InstAbility;
import com.anypluspay.channel.types.channel.MaintainTimeType;
import com.anypluspay.channel.types.enums.CardType;
import com.anypluspay.channel.types.enums.RequestType;
import com.anypluspay.channel.types.order.InstOrderStatus;
import com.anypluspay.commons.enums.CodeEnum;
import com.anypluspay.commons.response.ResponseResult;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 枚举值
 * @author wxj
 * 2024/12/30
 */
@RestController
@RequestMapping("/enum-value")
public class EnumValueController {

    private static final Map<String, List<EnumObject>> CODE_ENUM_CACHE_MAP = new HashMap<>();

    static {
        final List<Class<? extends CodeEnum>> CODE_ENUM_LIST = new ArrayList<>();

        // 系统管理
        CODE_ENUM_LIST.add(SysUserStatus.class);

        // 账务相关
        CODE_ENUM_LIST.add(AccountTitleType.class);
        CODE_ENUM_LIST.add(BalanceDirection.class);
        CODE_ENUM_LIST.add(AccountTitleScope.class);
        CODE_ENUM_LIST.add(DenyStatus.class);

        // 渠道相关
        CODE_ENUM_LIST.add(InstAbility.class);
        CODE_ENUM_LIST.add(ChannelApiType.class);
        CODE_ENUM_LIST.add(RequestType.class);
        CODE_ENUM_LIST.add(CardType.class);
        CODE_ENUM_LIST.add(MaintainTimeType.class);
        CODE_ENUM_LIST.add(InstOrderStatus.class);

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

