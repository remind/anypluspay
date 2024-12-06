package com.anypluspay.component.sequence.repository;

import com.anypluspay.component.sequence.domain.Sequence;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * jdbc 仓储实现
 * @author wxj
 * 2023/12/10
 */
public class JdbcSequenceRepository implements SequenceRepository {
    private static final String QUERY_ALL = " select * from tf_sequence ";
    private static final String LOCK_BY = " select * from tf_sequence where name = ? for update ";
    private static final String UPDATE = " update tf_sequence set current_value = ? where current_value = ? and name = ? ";
    private final RowMapper<Sequence> rowMapper = new RowMapper<Sequence>() {
        public Sequence mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
            Sequence sequence = new Sequence();
            sequence.setName(paramResultSet.getString(1));
            sequence.setCurrentValue(paramResultSet.getLong(2));
            sequence.setIncrement(paramResultSet.getInt(3));
            sequence.setTotal(paramResultSet.getInt(4));
            sequence.setThreshold(paramResultSet.getInt(5));
            return sequence;
        }
    };
    private final JdbcTemplate jdbcTemplate;

    public List<Sequence> loadAll() {
        return jdbcTemplate.query(QUERY_ALL, this.rowMapper);
    }

    public Sequence lock(String sequenceName) {
        return jdbcTemplate.queryForObject(LOCK_BY, this.rowMapper, sequenceName);
    }

    public void update(String sequenceName, Long beforeValue, Long afterValue) {
        jdbcTemplate.update(UPDATE, afterValue, beforeValue, sequenceName);
    }

    public JdbcSequenceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
