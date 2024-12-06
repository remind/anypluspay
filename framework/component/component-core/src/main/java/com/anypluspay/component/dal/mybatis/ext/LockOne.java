package com.anypluspay.component.dal.mybatis.ext;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @author wxj
 * 2023/12/19
 */
public class LockOne extends AbstractMethod {

    public LockOne() {
        this(ExtSqlMethod.LOCK_ONE.getMethod());
    }

    protected LockOne(String methodName) {
        super(methodName);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        ExtSqlMethod sqlMethod = ExtSqlMethod.LOCK_ONE;
        SqlSource sqlSource = super.createSqlSource(configuration, String.format(sqlMethod.getSql(),
                sqlFirst(), sqlSelectColumns(tableInfo, true), tableInfo.getTableName(),
                sqlWhereEntityWrapper(true, tableInfo), sqlComment()), modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
    }
}
