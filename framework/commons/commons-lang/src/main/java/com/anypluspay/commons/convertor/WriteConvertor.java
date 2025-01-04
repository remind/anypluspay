package com.anypluspay.commons.convertor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2023/12/16
 */
public interface WriteConvertor<EntityType, DoType> extends BaseExpressionConvertor{

    DoType toDO(EntityType entityType);

    default List<DoType> toDO(List<EntityType> entityTypes) {
        List<DoType> doTypes = new ArrayList<>();
        if (entityTypes != null) {
            entityTypes.forEach(entityType -> doTypes.add(toDO(entityType)));
        }
        return doTypes;
    }
}
