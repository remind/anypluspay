package com.anypluspay.anypay.infra.persistence.common;

import com.anypluspay.anypay.domain.common.Dictionary;
import com.anypluspay.anypay.domain.common.repository.DictionaryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DictionaryRepositoryImpl implements DictionaryRepository {

    @Override
    public Dictionary load(String type, String code) {
        return null;
    }

    @Override
    public List<Dictionary> getListByType(String type) {
        return List.of();
    }

    @Override
    public Map<String, List<Dictionary>> getListByTypes(List<String> types) {
        return Map.of();
    }
}
