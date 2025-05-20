package com.anypluspay.admin.account.controller;

import com.anypluspay.admin.account.model.dto.TitleDailyDto;
import com.anypluspay.admin.account.mapper.TitleDailyQueryMapper;
import com.anypluspay.admin.account.query.TitleDailyQuery;
import com.anypluspay.commons.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 试算平衡表
 *
 * @author wxj
 * 2025/1/21
 */
@RestController
@RequestMapping("/account/title-daily")
public class TitleDailyController {

    private static final DateTimeFormatter ACCOUNTING_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private TitleDailyQueryMapper dalMapper;

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public ResponseResult<List<TitleDailyDto>> list(TitleDailyQuery query) {
        if (StringUtils.isBlank(query.getAccountDate())) {
            query.setAccountDate(getYesterdayAccounting());
        }
        return ResponseResult.success(dalMapper.list(query));
    }

    private String getYesterdayAccounting() {
        return LocalDate.now().plusDays(-1).format(ACCOUNTING_DATE_FORMATTER);
    }
}
