package com.anypluspay.account.facade.manager.request;

import com.anypluspay.commons.validator.UpdateValidate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 科目请求
 *
 * @author wxj
 * 2023/12/16
 */
@Data
public class AccountTitleRequest {

    /**
     * 科目代码
     */
    @NotNull(message = "代码不能为空", groups = {UpdateValidate.class})
    private String code;

    /**
     * 科目名称
     */
    @NotNull(message = "名称不能为空")
    private String name;

    /**
     * 父科目代码
     */
    private String parentCode;

    /**
     * 是否有效
     */
    @NotNull(message = "是否有效不能为空")
    private Boolean enable;

    /**
     * 备注
     */
    private String memo;

    /**
     * 类型：1(资产类); 2(负债类); 3(所有者权益); 4(共同类); 5(损益类)
     * 一级科目必填
     */
    private String type;

    /**
     * 适用范围：1.内部科目;2,外部科目
     * 一级科目必填
     */
    private String scope;

}
