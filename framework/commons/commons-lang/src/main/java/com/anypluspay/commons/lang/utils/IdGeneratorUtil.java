package com.anypluspay.commons.lang.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * id生成器
 *
 * @author wxj
 * 2023/12/10
 */
public class IdGeneratorUtil {

    /**
     * 生成24位的ID，由 8位yyyyMMdd + 3位SystemCode + 3位bizType + 2位dbRouteId + 8位自增
     *
     * @param systemCode 系统编号
     * @param bizType    业务类型
     * @param dbRouteId  分表位
     * @param sequence   自增序列
     * @return 24位数字
     */
    public static String getId(String systemCode, String bizType, String dbRouteId, Long sequence) {
        if (StringUtils.isBlank(systemCode) || systemCode.length() != 3) {
            throw new IllegalArgumentException("系统编码长度不为3");
        }

        if (StringUtils.isBlank(bizType) || bizType.length() != 3) {
            throw new IllegalArgumentException("业务类型编码长度不为3");
        }

        if (StringUtils.isBlank(dbRouteId) || dbRouteId.length() != 2) {
            throw new IllegalArgumentException("分表位长度不为2");
        }

        if (sequence < 0 || sequence.toString().length() > 8) {
            throw new IllegalArgumentException("序列必须为8位以内的正数");
        }

        return DateFormatUtils.format(new Date(), "yyyyMMdd") +
                systemCode +
                bizType +
                dbRouteId +
                String.format("%08d", sequence);
    }

    /**
     * 根据id获取分表位
     *
     * @param id
     * @return
     */
    public static String reverseIdGetDbRouteId(String id) {
        return id.substring(14, 16);
    }

    public static String reverseIdGetSystemCode(String id) {
        return id.substring(8, 11);
    }

    /**
     * 根据会员ID获取分表位
     *
     * @param memberId
     * @return
     */
    public static String getDbRouteIdByMemberId(String memberId) {
        if (StringUtils.isBlank(memberId)) {
            throw new IllegalArgumentException("memberId不能为空");
        }
        return memberId.substring(memberId.length() - 3, memberId.length() - 1);
    }
}
