package com.anypluspay.anypay.domain.common.service;

import com.anypluspay.anypay.types.common.IdType;
import com.anypluspay.commons.enums.SystemCodeEnums;
import com.anypluspay.commons.lang.utils.IdGeneratorUtil;
import com.anypluspay.component.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wxj
 * 2024/1/15
 */
@Component
public class IdGeneratorService {

    @Autowired
    private SequenceService sequenceService;

    public String genTradeId(String memberId, IdType idType) {
        return sequenceService.getId(memberId, SystemCodeEnums.PAYMENT, idType);
    }

    public String genIdByRelateId(String relateId, IdType idType) {
        return sequenceService.getIdByRouteId(IdGeneratorUtil.reverseIdGetDbRouteId(relateId), SystemCodeEnums.PAYMENT, idType);
    }

    public IdType getIdType(String id) {
        String idTypeCode = IdGeneratorUtil.reverseIdGetBizType(id);
        return IdType.getByBizTypeCode(idTypeCode);
    }

}
