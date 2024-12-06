package com.anypluspay.commons.convertor;


import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2023/12/16
 */
public interface ReadConvertor<EntityType, DoType> extends BaseExpressionConvertor {

    EntityType toEntity(DoType doType);

    default List<EntityType> toEntity(List<DoType> doTypes) {
        List<EntityType> entityTypes = new ArrayList<>();
        if (doTypes != null) {
            doTypes.forEach(doType -> entityTypes.add(toEntity(doType)));
        }
        return entityTypes;
    }
}
