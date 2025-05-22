package com.anypluspay.admin.basis.mapper.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wxj
 * 2025/3/24
 */
@Data
@TableName("t_query_define")
public class QueryDefineDO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 组ID
     */
    private Long groupId;

    /**
     * 名称
     */
    private String name;

    /**
     * 数据源
     */
    private String dataSource;

    /**
     * 查询SQL
     */
    private String querySql;

    /**
     * 可支持参数，不可重复
     */
    private String paramName;

    /**
     * 排序
     */
    private int sort;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;
}
