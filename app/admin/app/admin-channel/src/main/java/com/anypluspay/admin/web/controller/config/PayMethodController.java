package com.anypluspay.admin.web.controller.config;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.admin.dao.convertor.config.PayMethodConvertor;
import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import com.anypluspay.admin.model.config.PayMethodDto;
import com.anypluspay.admin.model.query.PayMethodQuery;
import com.anypluspay.admin.model.request.PayMethodRequest;
import com.anypluspay.basis.web.controller.AbstractController;
import com.anypluspay.channel.infra.persistence.dataobject.PayMethodDO;
import com.anypluspay.channel.infra.persistence.mapper.PayMethodMapper;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 支付方式
 *
 * @author wxj
 * 2024/11/12
 */
@RestController
@RequestMapping("/pay-method")
public class PayMethodController extends AbstractController  {

    @Autowired
    private PayMethodMapper dalMapper;

    @Autowired
    private PayMethodConvertor convertor;

    /**
     * 支付方式列表查询
     *
     * @param query 查询参数
     * @return  查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<PayMethodDto>> list(PayMethodQuery query) {
        LambdaQueryWrapper<PayMethodDO> queryWrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(query.getCode())) {
            queryWrapper.like(PayMethodDO::getCode, query.getCode());
        }
        if (StrUtil.isNotBlank(query.getName())) {
            queryWrapper.like(PayMethodDO::getName, query.getName());
        }
        return ResponseResult.success(convertor.toDto(dalMapper.selectPage(getIPage(query), queryWrapper)));
    }

    /**
     * 根据主键查询详情
     *
     * @param code 主键
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResponseResult<PayMethodDto> detail(@RequestParam String code) {
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
        if (dalMapper.insert(convertor.requestToDo(request)) == 1) {
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
        if (dalMapper.updateById(convertor.requestToDo(request)) == 1) {
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
}
