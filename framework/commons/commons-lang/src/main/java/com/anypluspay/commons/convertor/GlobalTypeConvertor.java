package com.anypluspay.commons.convertor;

import com.anypluspay.commons.enums.CodeEnum;
import com.anypluspay.commons.enums.EnableEnum;
import com.anypluspay.commons.enums.ResultStatusEnum;
import com.anypluspay.commons.lang.utils.EnumUtil;
import cn.hutool.core.util.StrUtil;

import java.util.List;

/**
 * 全局类型转换
 *
 * @author wxj
 * 2024/1/4
 */
public interface GlobalTypeConvertor {

    default String getCode(CodeEnum codeEnum) {
        return codeEnum == null ? null : codeEnum.getCode();
    }

    default EnableEnum toEnableEnum(String code) {
        return EnumUtil.getByCode(EnableEnum.class, code);
    }

    default ResultStatusEnum toResultStatusEnum(String code) {
        return EnumUtil.getByCode(ResultStatusEnum.class, code);
    }

}
