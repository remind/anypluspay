package com.anypluspay.admin.payment.controller;

import com.anypluspay.admin.core.controller.AbstractController;
import com.anypluspay.admin.payment.convertor.PayMethodConvertor;
import com.anypluspay.admin.payment.query.PayMethodQuery;
import com.anypluspay.admin.payment.request.PayMethodRequest;
import com.anypluspay.admin.payment.response.PayMethodResponse;
import com.anypluspay.commons.lang.utils.AssertUtil;
import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import com.anypluspay.payment.infra.persistence.dataobject.PayMethodDO;
import com.anypluspay.payment.infra.persistence.mapper.PayMethodMapper;
import com.anypluspay.payment.types.asset.AssetType;
import com.anypluspay.payment.types.paymethod.PayMethodStatus;
import com.anypluspay.payment.types.paymethod.PayModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 支付方式管理
 *
 * @author wxj
 * 2025/5/28
 */
@RestController
@RequestMapping("/payment/pay-method")
public class PayMethodController extends AbstractController {

    @Autowired
    private PayMethodConvertor convertor;

    @Autowired
    private PayMethodMapper dalMapper;

    /**
     * 列表
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<PayMethodResponse>> list(PayMethodQuery query) {
        LambdaQueryWrapper<PayMethodDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getCode())) {
            queryWrapper.like(PayMethodDO::getCode, query.getCode());
        }

        if (StringUtils.isNotBlank(query.getName())) {
            queryWrapper.like(PayMethodDO::getName, query.getName());
        }

        queryWrapper.orderByDesc(PayMethodDO::getGmtCreate);
        IPage<PayMethodDO> page = getIPage(query);
        return ResponseResult.success(convertor.toDto(dalMapper.selectPage(page, queryWrapper)));
    }

    /**
     * 根据主键查询详情
     *
     * @param code 编码
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResponseResult<PayMethodResponse> detail(@RequestParam @NotBlank String code) {
        return ResponseResult.success(convertor.toDto(dalMapper.selectById(code)));
    }

    /**
     * 新增
     *
     * @param request 新增请求对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody @Validated(AddValidate.class) PayMethodRequest request) {
        validateEnums(request);
        PayMethodDO doObject = convertor.requestToDo(request);
        if (dalMapper.insert(doObject) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("新增失败");
        }
    }

    /**
     * 修改
     *
     * @param request 修改请求对象
     * @return 修改结果
     */
    @PostMapping("/update")
    public ResponseResult<String> update(@RequestBody @Validated(UpdateValidate.class) PayMethodRequest request) {
        PayMethodDO doObject = dalMapper.selectById(request.getCode());
        AssertUtil.notNull(doObject, "不存在");
        validateEnums(request);
        doObject.setName(request.getName());
        doObject.setPayInst(request.getPayInst());
        doObject.setMemo(request.getMemo());
        doObject.setExtension(request.getExtension());
        doObject.setStatus(request.getStatus());
        if (dalMapper.updateById(doObject) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param code 编码
     * @return 删除结果
     */
    @GetMapping("/delete")
    public ResponseResult<String> delete(@RequestParam String code) {
        if (dalMapper.deleteById(code) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("删除失败");
        }
    }

    /**
     * 验证枚举
     *
     * @param request 请求对象
     */
    private void validateEnums(PayMethodRequest request) {
        AssertUtil.notNull(EnumUtil.getByCode(PayMethodStatus.class, request.getStatus()), "状态值错误");
        AssertUtil.notNull(EnumUtil.getByCode(PayModel.class, request.getPayModel()), "支付模式值错误");
        AssertUtil.notNull(EnumUtil.getByCode(AssetType.class, request.getAssetType()), "资产类型值错误");
    }
}
