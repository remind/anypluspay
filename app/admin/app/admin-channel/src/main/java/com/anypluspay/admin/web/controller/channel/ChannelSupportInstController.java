package com.anypluspay.admin.web.controller.channel;

import com.anypluspay.admin.dao.convertor.channel.ChannelSupportInstConvertor;
import com.anypluspay.commons.validator.AddValidate;
import com.anypluspay.commons.validator.UpdateValidate;
import com.anypluspay.admin.model.channel.ChannelSupportInstDto;
import com.anypluspay.admin.model.request.ChannelSupportInstRequest;
import com.anypluspay.channel.infra.persistence.dataobject.ChannelSupportInstDO;
import com.anypluspay.channel.infra.persistence.mapper.ChannelSupportInstMapper;
import com.anypluspay.commons.response.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 渠道支持机构
 * @author wxj
 * 2024/11/28
 */
@RestController
@RequestMapping("/channel-support-inst")
public class ChannelSupportInstController {

    @Autowired
    private ChannelSupportInstMapper dalMapper;

    @Autowired
    private ChannelSupportInstConvertor convertor;

    /**
     * 按渠道编码查询
     *
     * @param channelCode 渠道编码
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<List<ChannelSupportInstDto>> list(@RequestParam String channelCode) {
        LambdaQueryWrapper<ChannelSupportInstDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChannelSupportInstDO::getChannelCode, channelCode);
        queryWrapper.orderByDesc(ChannelSupportInstDO::getTargetInstCode);
        return ResponseResult.success(convertor.toDto(dalMapper.selectList(queryWrapper)));
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResponseResult<ChannelSupportInstDto> detail(@RequestParam Long id) {
        return ResponseResult.success(convertor.toDto(dalMapper.selectById(id)));
    }

    /**
     * 新增
     *
     * @param request 新增请求对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody @Validated(AddValidate.class) ChannelSupportInstRequest request) {
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
    public ResponseResult<String> update(@RequestBody @Validated(UpdateValidate.class) ChannelSupportInstRequest request) {
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
