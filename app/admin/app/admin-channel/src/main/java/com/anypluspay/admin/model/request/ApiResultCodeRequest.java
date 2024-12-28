package com.anypluspay.admin.model.request;

import com.anypluspay.commons.validator.UpdateValidate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wxj
 * 2024/12/1
 */
@Data
public class ApiResultCodeRequest {

    /**
     * 主键
     */
    @NotNull(message = "ID不能为空", groups = UpdateValidate.class)
    private Long id;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 接口类型
     */
    private String apiType;

    /**
     * 机构API结果码
     */
    private String instApiCode;

    /**
     * 机构API子结果码
     */
    private String instApiSubCode;

    /**
     * 机构API消息
     */
    private String instApiMessage;

    /**
     * 统一结果码
     */
    private String unityCode;

    /**
     * 机构订单结果状态
     */
    private String resultStatus;

    /**
     * 是否映射
     */
    private Boolean useMapping;

    /**
     * 是否重路由
     */
    private Boolean reRouteEnable;
}
