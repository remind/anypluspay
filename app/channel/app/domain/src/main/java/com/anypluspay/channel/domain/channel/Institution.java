package com.anypluspay.channel.domain.channel;

import lombok.Data;

import java.util.List;

/**
 * 机构
 * @author wxj
 * 2024/10/31
 */
@Data
public class Institution {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 机构能力
     */
    private List<String> InstAbility;

    /**
     * 备注
     */
    private String memo;
}
