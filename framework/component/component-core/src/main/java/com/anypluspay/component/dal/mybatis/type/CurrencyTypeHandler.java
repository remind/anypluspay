package com.anypluspay.component.dal.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

/**
 * 币种类型转换
 * @author wxj
 * 2024/11/14
 */
public class CurrencyTypeHandler extends BaseTypeHandler<Currency> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Currency parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCurrencyCode());
    }

    @Override
    public Currency getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Currency.getInstance(rs.getString(columnName));
    }

    @Override
    public Currency getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Currency.getInstance(rs.getString(columnIndex));
    }

    @Override
    public Currency getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Currency.getInstance(cs.getString(columnIndex));
    }
}
