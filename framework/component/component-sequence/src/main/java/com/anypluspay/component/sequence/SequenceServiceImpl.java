package com.anypluspay.component.sequence;


import com.anypluspay.commons.enums.BizIdType;
import com.anypluspay.commons.enums.SystemCodeEnums;
import com.anypluspay.commons.lang.utils.IdGeneratorUtil;

/**
 * 序列实现
 *
 * @author wxj
 * 2023/12/10
 */
public class SequenceServiceImpl implements SequenceService {

    private final SequenceInnerService sequenceInnerService;

    @Override
    public Long getNext(String sequenceName) {
        return sequenceInnerService.next(sequenceName);
    }

    @Override
    public String getId(String memberId, SystemCodeEnums systemCodeEnums, BizIdType idType) {
        return IdGeneratorUtil.getId(systemCodeEnums.getCode(), idType.getBizTypeCode()
                , IdGeneratorUtil.getDbRouteIdByMemberId(memberId), getNext(idType.getSeqName()));
    }

    @Override
    public String getIdByRouteId(String routeId, SystemCodeEnums systemCodeEnums, BizIdType idType) {
        return IdGeneratorUtil.getId(systemCodeEnums.getCode(), idType.getBizTypeCode()
                , routeId, getNext(idType.getSeqName()));
    }

    @Override
    public String getIdByRelateId(String relateId, BizIdType idType) {
        return IdGeneratorUtil.getId(IdGeneratorUtil.reverseIdGetSystemCode(relateId), idType.getBizTypeCode()
                , IdGeneratorUtil.reverseIdGetDbRouteId(relateId), getNext(idType.getSeqName()));
    }

    public SequenceServiceImpl(SequenceInnerService sequenceInnerService) {
        this.sequenceInnerService = sequenceInnerService;
    }
}
