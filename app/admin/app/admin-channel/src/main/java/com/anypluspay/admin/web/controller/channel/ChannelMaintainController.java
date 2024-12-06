package com.anypluspay.admin.web.controller.channel;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.admin.dao.convertor.channel.ChannelMaintainConvertor;
import com.anypluspay.admin.model.AddValidate;
import com.anypluspay.admin.model.UpdateValidate;
import com.anypluspay.admin.model.channel.ChannelMaintainDto;
import com.anypluspay.admin.model.query.ChannelMaintainQuery;
import com.anypluspay.admin.model.request.ChannelMaintainRequest;
import com.anypluspay.admin.web.controller.AbstractController;
import com.anypluspay.channel.infra.persistence.dataobject.ChannelMaintainDO;
import com.anypluspay.channel.infra.persistence.mapper.ChannelMaintainMapper;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 渠道维护期
 *
 * @author wxj
 * 2024/12/3
 */
@RestController
@RequestMapping("/channel-maintain")
public class ChannelMaintainController extends AbstractController {

    @Autowired
    private ChannelMaintainMapper dalMapper;

    @Autowired
    private ChannelMaintainConvertor convertor;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<ChannelMaintainDto>> pageQuery(ChannelMaintainQuery query) {
        LambdaQueryWrapper<ChannelMaintainDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getChannelCode())) {
            queryWrapper.like(ChannelMaintainDO::getChannelCode, query.getChannelCode());
        }
        if (query.getGmtStart() != null) {
            queryWrapper.ge(ChannelMaintainDO::getGmtCreate, query.getGmtStart());
        }
        if (query.getGmtEnd() != null) {
            queryWrapper.lt(ChannelMaintainDO::getGmtCreate, query.getGmtEnd().plusDays(1));
        }
        queryWrapper.orderByDesc(ChannelMaintainDO::getGmtCreate);
        return ResponseResult.success(convertor.toDto(dalMapper.selectPage(getIPage(query), queryWrapper)));
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResponseResult<ChannelMaintainDto> detail(@RequestParam Long id) {
        return ResponseResult.success(convertor.toDto(dalMapper.selectById(id)));
    }

    /**
     * 新增
     *
     * @param request 新增请求对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody @Validated(AddValidate.class) ChannelMaintainRequest request) {
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
    public ResponseResult<String> update(@RequestBody @Validated(UpdateValidate.class) ChannelMaintainRequest request) {
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
