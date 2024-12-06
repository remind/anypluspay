package com.anypluspay.admin.web.controller.config;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.admin.dao.convertor.config.InstitutionConvertor;
import com.anypluspay.admin.model.AddValidate;
import com.anypluspay.admin.model.request.InstitutionRequest;
import com.anypluspay.admin.model.UpdateValidate;
import com.anypluspay.admin.model.config.InstitutionDto;
import com.anypluspay.admin.model.query.InstQuery;
import com.anypluspay.admin.web.controller.AbstractController;
import com.anypluspay.channel.infra.persistence.dataobject.InstitutionDO;
import com.anypluspay.channel.infra.persistence.mapper.InstitutionMapper;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 机构管理
 *
 * @author wxj
 * 2024/11/2
 */
@RequestMapping("/institution")
@RestController
public class InstitutionController extends AbstractController {

    @Autowired
    private InstitutionMapper dalMapper;

    @Autowired
    private InstitutionConvertor convertor;

    /**
     * 机构列表查询
     *
     * @param query 查询参数
     * @return  查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<InstitutionDto>> list(InstQuery query) {
        LambdaQueryWrapper<InstitutionDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getCode())) {
            queryWrapper.like(InstitutionDO::getCode, query.getCode());
        }
        if (StrUtil.isNotBlank(query.getName())) {
            queryWrapper.like(InstitutionDO::getName, query.getName());
        }
        if (StrUtil.isNotBlank(query.getType())) {
            queryWrapper.like(InstitutionDO::getInstAbility, query.getType());
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
    public ResponseResult<InstitutionDto> detail(@RequestParam String code) {
        return ResponseResult.success(convertor.toDto(dalMapper.selectById(code)));
    }

    /**
     * 新增
     *
     * @param request 新增请求对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody @Validated(AddValidate.class) InstitutionRequest request) {
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
    public ResponseResult<String> update(@RequestBody @Validated(UpdateValidate.class) InstitutionRequest request) {
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
