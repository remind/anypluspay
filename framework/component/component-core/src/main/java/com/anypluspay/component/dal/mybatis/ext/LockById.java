package com.anypluspay.component.dal.mybatis.ext;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author wxj
 * 2023/12/18
 */
public class LockById extends AbstractMethod {

    public LockById() {
        this(ExtSqlMethod.LOCK_BY_ID.getMethod());
    }

    public LockById(String name) {
        super(name);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        ExtSqlMethod extSqlMethod = ExtSqlMethod.LOCK_BY_ID;
        SqlSource sqlSource = super.createSqlSource(configuration, String.format(extSqlMethod.getSql(),
                sqlSelectColumns(tableInfo, false),
                tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty(),
                tableInfo.getLogicDeleteSql(true, true)), Object.class);
        return this.addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
    }
}
