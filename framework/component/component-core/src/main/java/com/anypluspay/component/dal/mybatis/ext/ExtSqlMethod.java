package com.anypluspay.component.dal.mybatis.ext;

/**
 * @author wxj
 * 2023/12/18
 */
public enum ExtSqlMethod {
    LOCK_BY_ID("lockById", "根据ID 锁定一条数据", "SELECT %s FROM %s WHERE %s=#{%s} %s FOR UPDATE"),
    LOCK_ONE("lockOne", "锁定满足条件的一条数据", "<script>%s SELECT %s FROM %s %s %s FOR UPDATE\n</script>"),

    ;
    private final String method;
    private final String desc;
    private final String sql;

    ExtSqlMethod(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }
}
