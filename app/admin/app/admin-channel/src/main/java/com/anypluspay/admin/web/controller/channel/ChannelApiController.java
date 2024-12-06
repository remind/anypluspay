package com.anypluspay.admin.web.controller.channel;

import com.anypluspay.admin.dao.convertor.channel.ChannelApiConvertor;
import com.anypluspay.admin.model.AddValidate;
import com.anypluspay.admin.model.UpdateValidate;
import com.anypluspay.admin.model.channel.ChannelApiDto;
import com.anypluspay.admin.model.request.ChannelApiRequest;
import com.anypluspay.admin.web.controller.AbstractController;
import com.anypluspay.channel.infra.persistence.dataobject.ChannelApiDO;
import com.anypluspay.channel.infra.persistence.mapper.ChannelApiMapper;
import com.anypluspay.commons.response.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 渠道接口
 *
 * @author wxj
 * 2024/8/29
 */
@RestController
@RequestMapping("/channel-api")
public class ChannelApiController extends AbstractController {

    @Autowired
    private ChannelApiMapper dalMapper;

    @Autowired
    private ChannelApiConvertor convertor;

    /**
     * 按渠道编码查询
     *
     * @param channelCode 渠道编码
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<List<ChannelApiDto>> list(@RequestParam @Validated @NotBlank String channelCode) {
        LambdaQueryWrapper<ChannelApiDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChannelApiDO::getChannelCode, channelCode);
        queryWrapper.orderByDesc(ChannelApiDO::getGmtModified);
        return ResponseResult.success(convertor.toDto(dalMapper.selectList(queryWrapper)));
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return 查询结果
     */
    @GetMapping("/detail")
    public ResponseResult<ChannelApiDto> detail(@RequestParam Long id) {
        return ResponseResult.success(convertor.toDto(dalMapper.selectById(id)));
    }

    /**
     * 新增
     *
     * @param request 新增请求对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody @Validated(AddValidate.class) ChannelApiRequest request) {
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
    public ResponseResult<String> update(@RequestBody @Validated(UpdateValidate.class) ChannelApiRequest request) {
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
