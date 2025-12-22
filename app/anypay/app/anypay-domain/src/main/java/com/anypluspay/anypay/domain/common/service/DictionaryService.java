package com.anypluspay.anypay.domain.common.service;

import com.anypluspay.anypay.domain.common.Dictionary;
import com.anypluspay.anypay.domain.common.repository.DictionaryRepository;
import com.anypluspay.anypay.types.common.DictionaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wxj
 * 2025/12/22
 */
@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    public List<Dictionary> list(DictionaryType dictionaryType) {
        return dictionaryRepository.getListByType(dictionaryType.getCode());
    }
}
