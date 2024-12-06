package com.anypluspay.channel.infra.persistence;

import com.anypluspay.channel.domain.repository.UnityResultCodeRepository;
import com.anypluspay.channel.domain.result.UnityResultCode;
import com.anypluspay.channel.infra.persistence.convertor.UnityResultCodeDalConvertor;
import com.anypluspay.channel.infra.persistence.mapper.UnityResultCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2024/8/7
 */
@Repository
public class UnityResultCodeRepositoryImpl implements UnityResultCodeRepository {

    @Autowired
    private UnityResultCodeDalConvertor unityResultCodeDalConvertor;

    @Autowired
    private UnityResultCodeMapper unityResultCodeMapper;

    @Override
    public UnityResultCode load(String unityCode) {
        return unityResultCodeDalConvertor.convert(unityResultCodeMapper.selectById(unityCode));
    }
}
