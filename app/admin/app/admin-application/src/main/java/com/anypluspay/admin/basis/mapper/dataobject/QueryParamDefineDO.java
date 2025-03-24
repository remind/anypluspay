package com.anypluspay.admin.basis.mapper.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 查询参数定义
 * @author wxj
 * 2025/3/24
 */
@Data
@TableName("t_query_param_define")
public class QueryParamDefineDO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 显示名称
     */
    private String label;

    /**
     * 是否可搜索
     */
    private boolean search;

    /**
     * 搜索排序
     */
    private int sort;

    /**
     * 显示类型
     */
    private String showType;

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
