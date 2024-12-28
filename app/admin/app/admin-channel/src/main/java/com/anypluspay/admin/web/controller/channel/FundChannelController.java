package com.anypluspay.admin.web.controller.channel;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.admin.dao.convertor.channel.FundChannelConvertor;
import com.anypluspay.admin.model.AddValidate;
import com.anypluspay.admin.model.UpdateValidate;
import com.anypluspay.admin.model.channel.FundChannelDto;
import com.anypluspay.admin.model.query.FundChannelQuery;
import com.anypluspay.admin.model.request.FundChannelRequest;
import com.anypluspay.basis.web.controller.AbstractController;
import com.anypluspay.channel.infra.persistence.dataobject.*;
import com.anypluspay.channel.infra.persistence.mapper.*;
import com.anypluspay.commons.response.ResponseResult;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 资金渠道
 *
 * @author wxj
 * 2024/11/12
 */
@RestController
@RequestMapping("/fund-channel")
public class FundChannelController extends AbstractController {

    @Autowired
    private FundChannelMapper dalMapper;

    @Autowired
    private ChannelApiMapper channelApiMapper;

    @Autowired
    private ApiResultCodeMapper apiResultCodeMapper;

    @Autowired
    private ChannelSupportInstMapper channelSupportInstMapper;

    @Autowired
    private ChannelMaintainMapper channelMaintainMapper;

    @Autowired
    private FundChannelConvertor convertor;

    @Qualifier("channelTransactionTemplate")
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 分页查询资金渠道
     *
     * @param query 查询参数
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<PageResult<FundChannelDto>> list(FundChannelQuery query) {
        LambdaQueryWrapper<FundChannelDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getCode())) {
            queryWrapper.like(FundChannelDO::getCode, query.getCode());
        }
        if (StrUtil.isNotBlank(query.getName())) {
            queryWrapper.like(FundChannelDO::getName, query.getName());
        }
        if (query.getEnable() != null) {
            queryWrapper.eq(FundChannelDO::getEnable, query.getEnable());
        }
        queryWrapper.orderByDesc(FundChannelDO::getGmtCreate);
        return ResponseResult.success(convertor.toDto(dalMapper.selectPage(getIPage(query), queryWrapper)));
    }

    /**
     * 资金渠道详情
     *
     * @param code 编码
     * @return 详情
     */
    @GetMapping("/detail")
    public ResponseResult<FundChannelDto> detail(@RequestParam @Validated @NotNull String code) {
        return ResponseResult.success(convertor.toDto(dalMapper.selectById(code)));
    }

    /**
     * 新增资金渠道
     *
     * @param request 新增对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseResult<String> add(@RequestBody @Validated(AddValidate.class) FundChannelRequest request) {
        if (dalMapper.insert(convertor.requestToDo(request)) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("新增失败");
        }
    }

    /**
     * 修改资金渠道
     *
     * @param request 修改对象
     * @return 修改结果
     */
    @PostMapping("/update")
    public ResponseResult<String> update(@RequestBody @Validated(UpdateValidate.class) FundChannelRequest request) {
        if (dalMapper.updateById(convertor.requestToDo(request)) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("修改失败");
        }
    }

    /**
     * 删除资金渠道
     *
     * @param code 编码
     * @return 删除结果
     */
    @GetMapping("/delete")
    public ResponseResult<String> delete(@RequestParam String code) {
        transactionTemplate.executeWithoutResult(status -> {
            if (dalMapper.deleteById(code) == 1) {
                LambdaQueryWrapper<ChannelApiDO> apiQuery = Wrappers.lambdaQuery();
                apiQuery.eq(ChannelApiDO::getChannelCode, code);
                channelApiMapper.delete(apiQuery);

                LambdaQueryWrapper<ApiResultCodeDO> resultCodeQuery = Wrappers.lambdaQuery();
                resultCodeQuery.eq(ApiResultCodeDO::getChannelCode, code);
                apiResultCodeMapper.delete(resultCodeQuery);

                LambdaQueryWrapper<ChannelSupportInstDO> instQuery = Wrappers.lambdaQuery();
                instQuery.eq(ChannelSupportInstDO::getChannelCode, code);
                channelSupportInstMapper.delete(instQuery);

                LambdaQueryWrapper<ChannelMaintainDO> maintainQuery = Wrappers.lambdaQuery();
                maintainQuery.eq(ChannelMaintainDO::getChannelCode, code);
                channelMaintainMapper.delete(maintainQuery);
            }
        });
        return ResponseResult.success();
    }
}
