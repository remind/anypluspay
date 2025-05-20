
## 试用平衡

是通过事件触发调用存储过程实现的，如果中间有空缺时间，会存在递归调用，需要确认是否允许递归及深度


相关SQL:

```sql
    -- 查询是否允许递归及深度
    SHOW VARIABLES LIKE 'max_sp_recursion_depth';
    
    -- 会话级修改
    SET SESSION max_sp_recursion_depth = 255;

    -- 全局修改（需要重启）
    SET GLOBAL max_sp_recursion_depth = 255;
    
    -- 执行
    BEGIN
    call proc_title_daily_stat('20250114', @result_code, @error_level, @error_message);
    select @result_code, @error_level, @error_message;
    END;
    
    -- 查询结果
    select * from t_dbajob_log;
    select * from t_dbajob_conf;
    select * from t_title_daily;
    
    -- 清理
    update t_dbajob_conf set job_status = '20250101';
    delete from t_dbajob_log;
    delete from t_title_daily;

    -- 创建事件
    DROP EVENT IF exists daily_event;
    CREATE EVENT daily_event
    ON SCHEDULE EVERY 1 DAY
    STARTS TIMESTAMP(CURRENT_DATE, '01:00:00')
    DO
      call proc_title_daily_stat(DATE_FORMAT(now()-INTERVAL 1 DAY,'%Y%m%d'), @result_code, @error_level, @error_message);
```