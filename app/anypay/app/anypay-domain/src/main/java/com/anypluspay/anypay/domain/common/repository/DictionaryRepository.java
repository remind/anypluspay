package com.anypluspay.anypay.domain.common.repository;


import com.anypluspay.anypay.domain.common.Dictionary;

import java.util.List;
import java.util.Map;

/**
 * @author wxj
 * 2025/9/10
 */
public interface DictionaryRepository {

    Dictionary load(String type, String code);

    List<Dictionary> getListByType(String type);

    Map<String, List<Dictionary>> getListByTypes(List<String> types);
}
