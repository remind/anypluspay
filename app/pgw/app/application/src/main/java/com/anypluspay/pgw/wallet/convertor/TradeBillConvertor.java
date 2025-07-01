package com.anypluspay.pgw.wallet.convertor;

import com.anypluspay.commons.response.page.PageResult;
import com.anypluspay.payment.types.trade.query.TradeBillDto;
import com.anypluspay.pgw.wallet.response.TradeBillResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.stream.Collectors;

/**
 * @author wxj
 * 2025/7/1
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TradeBillConvertor {

    TradeBillResponse convert(TradeBillDto tradeBillDto);

    default PageResult<TradeBillResponse> convert(PageResult<TradeBillDto> tradeBillDtos) {
        PageResult<TradeBillResponse> result = new PageResult<>();
        result.setPage(tradeBillDtos.getPage());
        result.setPageSize(tradeBillDtos.getPageSize());
        result.setTotal(tradeBillDtos.getTotal());
        result.setItems(tradeBillDtos.getItems().stream().map(this::convert).collect(Collectors.toList()));
        return result;
    }
}
