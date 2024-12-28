package com.anypluspay.admin.web.controller.channel;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.admin.dao.convertor.channel.ApiResultCodeConvertor;
import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import com.anypluspay.admin.model.channel.ApiResultCodeDto;
import com.anypluspay.admin.model.query.ApiResultCodeQuery;
import com.anypluspay.admin.model.request.ApiResultCodeRequest;
import com.anypluspay.basis.web.controller.AbstractController;
import com.anypluspay.channel.infra.persistence.dataobject.ApiResultCodeDO;
import com.anypluspay.channel.infra.persistence.mapper.ApiResultCodeMapper;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * API结果码
 *
 * @author wxj
 * 2024/12/1
 */
@RestController
@RequestMapping("/api-result-code")
public class ApiResultCodeController extends AbstractController {

    @Autowired
    private ApiResultCodeMapper dalMapper;

    @Autowired
    private ApiResultCodeConvertor convertor;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<ApiResultCodeDto>> pageQuery(ApiResultCodeQuery query) {
        LambdaQueryWrapper<ApiResultCodeDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getChannelCode())) {
            queryWrapper.like(ApiResultCodeDO::getChannelCode, query.getChannelCode());
        }
        if (StrUtil.isNotBlank(query.getApiType())) {
            queryWrapper.like(ApiResultCodeDO::getApiType, query.getApiType());
        }
        if (query.getUseMapping() != null) {
            queryWrapper.eq(ApiResultCodeDO::getUseMapping, query.getUseMapping());
        }
        return ResponseResult.success(convertor.toDto(dalMapper.selectPage(getIPage(query), queryWrapper)));
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResponseResult<ApiResultCodeDto> detail(@RequestParam Long id) {
        return ResponseResult.success(convertor.toDto(dalMapper.selectById(id)));
    }

    /**
     * 新增
     *
     * @param request 新增请求对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody @Validated(AddValidate.class) ApiResultCodeRequest request) {
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
    public ResponseResult<String> update(@RequestBody @Validated(UpdateValidate.class) ApiResultCodeRequest request) {
        if (dalMapper.updateById(convertor.requestToDo(request)) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param id 主键ID
     * @return 删除结果
     */
    @GetMapping("/delete")
    public ResponseResult<String> delete(@RequestParam Long id) {
        if (dalMapper.deleteById(id) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("删除失败");
        }
    }
}
