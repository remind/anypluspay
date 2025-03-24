package com.anypluspay.admin.basis.service.unionquery;

import cn.hutool.core.collection.CollectionUtil;
import com.anypluspay.admin.basis.mapper.QueryDefineMapper;
import com.anypluspay.admin.basis.mapper.QueryParamDefineMapper;
import com.anypluspay.admin.basis.mapper.dataobject.QueryDefineDO;
import com.anypluspay.admin.basis.mapper.dataobject.QueryParamDefineDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 联合查询服务
 *
 * @author wxj
 * 2025/3/20
 */
@Service
public class UnionQueryService {

    @Autowired
    private QueryDefineMapper queryDefineMapper;

    @Autowired
    private QueryParamDefineMapper queryParamDefineMapper;

    @Qualifier("jdbcTemplateMap")
    @Autowired
    private Map<String, JdbcTemplate> jdbcTemplateMap;

    private final Map<String, QueryParamConfig> configMap = new HashMap<>();

    /**
     * 查询
     *
     * @param inParamName  入参名称
     * @param inParamValue 入参值
     */
    public List<UnionQueryResult> query(String inParamName, String inParamValue) {
        Map<String, ParamQueryStatus> queryParam = new HashMap<>();
        queryParam.put(inParamName, new ParamQueryStatus(Collections.singletonList(inParamValue), false));
        Map<String, QueryResult> queryConfigResultMap = new HashMap<>();
        while (!isAllQueryFinish(queryParam)) {
            Optional<Map.Entry<String, ParamQueryStatus>> entryOptional = queryParam.entrySet().stream().filter(e -> !e.getValue().isFinish()).findFirst();
            if (entryOptional.isPresent()) {
                ParamQueryStatus paramQueryStatus = entryOptional.get().getValue();
                Map<String, List<String>> outParamValueMap = executeSql(entryOptional.get().getKey(), paramQueryStatus.getParamValue(), queryConfigResultMap);
                paramQueryStatus.setFinish(true);
                queryParam.put(entryOptional.get().getKey(), paramQueryStatus);

                // 将输出参数转成输入参数
                if (outParamValueMap != null) {
                    outToInParam(queryParam, outParamValueMap);
                }
            }
        }
        List<QueryResult> queryResults = queryConfigResultMap.entrySet().stream()
                .sorted(Comparator.comparingInt(o -> o.getValue().getQueryDefine().getSort()))
                .map(Map.Entry::getValue)
                .toList();
        List<UnionQueryResult> unionQueryResults = new ArrayList<>();
        queryResults.forEach(v -> {
            unionQueryResults.add(new UnionQueryResult(v.getQueryDefine().getName(), v.getQueryDefine().getSort(), transferLabel(v.getItem())));
        });
        return unionQueryResults;
    }

    /**
     * 转换标签
     *
     * @param items
     * @return
     */
    private List<Map<String, Object>> transferLabel(List<Map<String, Object>> items) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(items)) {
            items.forEach(item -> {
                Map<String, Object> newItem = new HashMap<>();
                item.forEach((key, value) -> {
                    if (configMap.containsKey(key)) {
                        QueryParamDefineDO queryParamDefineDO = configMap.get(key).getQueryParamDefineDO();
                        newItem.put(queryParamDefineDO.getLabel(), value);
                    } else {
                        newItem.put(key, value);
                    }
                });
                result.add(newItem);
            });
        }
        return result;
    }

    /**
     * 执行SQL
     *
     * @param inParamName
     * @param inParamValue
     */
    private Map<String, List<String>> executeSql(String inParamName, List<String> inParamValue, Map<String, QueryResult> queryConfigResultMap) {
        Map<String, List<String>> outParamValueMap = new HashMap<>();
        List<QueryDefineDO> queryDefines = configMap.get(inParamName).getQueryDefineDOS();
        if (CollectionUtils.isEmpty(queryDefines)) {
            return null;
        }
        for (QueryDefineDO queryDefine : queryDefines) {
            List<String> paramQueryValue = inParamValue;

            // 从已经查询过的查询结果中，找出未查询过的参数
            if (queryConfigResultMap.containsKey(queryDefine.getName())) {
                QueryResult queryResult = queryConfigResultMap.get(queryDefine.getName());
                if (queryResult.getParamValueMap().containsKey(inParamName)) {
                    paramQueryValue = (List<String>) CollectionUtil.subtract(inParamValue, queryResult.getParamValueMap().get(inParamName));
                }
            }
            if (CollectionUtils.isEmpty(paramQueryValue)) {
                continue;
            }

            // 执行查询
            JdbcTemplate jdbcTemplate = jdbcTemplateMap.get(queryDefine.getDataSource());
            String sql = buildQuerySQL(inParamName, queryDefine.getQuerySql(), inParamValue);
            List<Map<String, Object>> queryData = jdbcTemplate.queryForList(sql, inParamValue.toArray());
            if (CollectionUtils.isEmpty(queryData)) {
                continue;
            }
            String[] params = StringUtils.split(queryDefine.getParamName(), ",");
            for (String outParam : params) {
                queryData.forEach(data -> {
                    if (data.containsKey(outParam) && data.get(outParam) != null) {
                        String paramStringValue = data.get(outParam).toString();

                        // 新增生成输出参数
                        if (outParamValueMap.containsKey(outParam)) {
                            outParamValueMap.get(outParam).add(data.get(outParam).toString());
                        } else {
                            outParamValueMap.put(outParam, Lists.newArrayList(paramStringValue));
                        }

                        // 增加配置已查询过的参数值记录
                        if (queryConfigResultMap.containsKey(queryDefine.getName())) {
                            if (queryConfigResultMap.get(queryDefine.getName()).getParamValueMap().containsKey(outParam)) {
                                List<String> paramValueList = queryConfigResultMap.get(queryDefine.getName()).getParamValueMap().get(outParam);
                                if (!paramValueList.contains(paramStringValue)) {
                                    paramValueList.add(paramStringValue);
                                }
                            } else {
                                queryConfigResultMap.get(queryDefine.getName()).getParamValueMap().put(outParam, Lists.newArrayList(paramStringValue));
                            }
                        } else {
                            Map<String, List<String>> tmpParamValueMap = new HashMap<>();
                            tmpParamValueMap.put(outParam, Lists.newArrayList(paramStringValue));
                            queryConfigResultMap.put(queryDefine.getName(), new QueryResult(queryDefine, tmpParamValueMap, new ArrayList<>()));
                        }
                    }
                });
            }

            // 增加查询结果
            if (queryConfigResultMap.get(queryDefine.getName()).getItem() == null) {
                queryConfigResultMap.get(queryDefine.getName()).setItem(queryData);
            } else {
                queryConfigResultMap.get(queryDefine.getName()).getItem().addAll(queryData);
            }
        }
        return outParamValueMap;
    }

    /**
     * 构建查询sql
     * @param inParamName
     * @param sql
     * @param inParamValue
     * @return
     */
    private String buildQuerySQL(String inParamName, String sql, List<String> inParamValue) {
        String inSql = inParamValue.stream().map(param -> "?").collect(Collectors.joining(", "));
        return "select * from (" + sql + ") t where " + inParamName + " IN ( " + inSql + " )";
    }

    /**
     * 输出参数转到输入参数
     *
     * @param queryStatusMap
     * @param outParamValueMap
     */
    private void outToInParam(Map<String, ParamQueryStatus> queryStatusMap, Map<String, List<String>> outParamValueMap) {
        outParamValueMap.forEach((k, v) -> {
            if (!CollectionUtils.isEmpty(v)) {
                if (queryStatusMap.containsKey(k)) {
                    ParamQueryStatus paramQueryStatus = queryStatusMap.get(k);
                    v.forEach(v1 -> {
                        if (!paramQueryStatus.getParamValue().contains(v1)) {
                            paramQueryStatus.getParamValue().add(v1);
                            paramQueryStatus.setFinish(false);
                        }
                    });
                } else {
                    queryStatusMap.put(k, new ParamQueryStatus(v, false));
                }
            }
        });
    }

    /**
     * 是否全部查询完成
     *
     * @param queryParam 查询参数
     * @return
     */
    private boolean isAllQueryFinish(Map<String, ParamQueryStatus> queryParam) {
        for (ParamQueryStatus paramQueryStatus : queryParam.values()) {
            if (!paramQueryStatus.isFinish()) {
                return false;
            }
        }
        return true;
    }

    @PostConstruct
    public void init() {
        List<QueryDefineDO> allQueryDefines = queryDefineMapper.selectList(Wrappers.emptyWrapper());

        Map<String, List<QueryDefineDO>> queryDefineMap = new HashMap<>();
        allQueryDefines.forEach(queryDefine -> {
            String[] inParams = StringUtils.split(queryDefine.getParamName(), ",");
            for (String inParam : inParams) {
                if (queryDefineMap.containsKey(inParam)) {
                    queryDefineMap.get(inParam).add(queryDefine);
                } else {
                    List<QueryDefineDO> queryDefines = new ArrayList<>();
                    queryDefines.add(queryDefine);
                    queryDefineMap.put(inParam, queryDefines);
                }
                queryDefineMap.get(inParam).sort(Comparator.comparingInt(QueryDefineDO::getSort));
            }
        });

        List<QueryParamDefineDO> queryParamDefineDOS = queryParamDefineMapper.selectList(Wrappers.emptyWrapper());
        queryParamDefineDOS.forEach(queryParamDefineDO -> {
            configMap.put(queryParamDefineDO.getName(), new QueryParamConfig(queryParamDefineDO.getName(), queryParamDefineDO, queryDefineMap.get(queryParamDefineDO.getName())));
        });
    }

}
