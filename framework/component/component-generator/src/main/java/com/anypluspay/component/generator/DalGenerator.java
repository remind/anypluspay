package com.anypluspay.component.generator;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.component.dal.mybatis.ext.ExtBaseMapper;
import com.anypluspay.component.generator.config.GenConfig;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.sql.Types;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author wxj
 * 2024/1/12
 */
public class DalGenerator {

    private final List<GenTableInfo> genTableInfos = new ArrayList<>();

    private final GenConfig genConfig;

    public DalGenerator(GenConfig genConfig) {
        this.genConfig = genConfig;
        init();
    }

    public void execute(String projectPath, String packageName) {
        String classDir = projectPath + "/src/main/java";
        String xmlDir = projectPath + "/src/main/resources/mapper";
        if (StrUtil.isNotBlank(packageName)) {
            xmlDir += "/" + packageName;
        }

        FastAutoGenerator.create(genConfig.getDb().getDataSource().getUrl()
                        , genConfig.getDb().getDataSource().getUser()
                        , genConfig.getDb().getDataSource().getPassword())
                .globalConfig(builder -> {
                    builder.author(genConfig.getProject().getAuthor()) // 设置作者
                            .outputDir(classDir); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builderPackage(xmlDir, packageName))
                .injectionConfig(builder -> {
                    builder.beforeOutputFile((tableInfo, stringObjectMap) -> {
                        GenTableInfo genTableInfo = getTableConfig(tableInfo.getName());
                        if (genTableInfo == null) {
                            return;
                        }

                        if (StrUtil.isNotBlank(genTableInfo.getIdName())) {
                            List<TableField> fields = tableInfo.getFields();
                            Iterator<TableField> iterator = fields.iterator();
                            while (iterator.hasNext()) {
                                TableField field = iterator.next();
                                if (field.getName().equals(genTableInfo.getIdName())) {
                                    field.primaryKey(false);
                                }
                            }
                        }

                    }).build();
                })
                .strategyConfig(builder())
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }

    private Consumer<PackageConfig.Builder> builderPackage(String xmlDir, String packageName) {
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.xml, xmlDir);
        pathInfo.put(OutputFile.controller, "");
        pathInfo.put(OutputFile.service, "");
        pathInfo.put(OutputFile.serviceImpl, "");


        return builder -> {
            String parentPackage = "com.anypluspay." + genConfig.getProject().getName() + ".infra.persistence";
            if (StrUtil.isNotBlank(packageName)) {
                parentPackage = parentPackage + "." + packageName;
            }
            builder.parent(parentPackage) // 设置父包名
                    .pathInfo(pathInfo)
                    .entity("dataobject"); // 设置mapperXml生成路径
        };
    }

    private Consumer<StrategyConfig.Builder> builder() {
        return builder -> {
            builder.addInclude(getTableNames())
                    .addTablePrefix(genConfig.getDb().getTable().getPrefix());
            builder.serviceBuilder().disableServiceImpl().disableService();
            builder.controllerBuilder().disable();
            builderEntity(builder.entityBuilder());
            builderMapper(builder.mapperBuilder());
        };
    }

    private static void builderEntity(Entity.Builder builder) {
        builder.formatFileName("%sDO")
                .addIgnoreColumns("auto_id") // auto_id不生成
                .idType(IdType.NONE)
                .addTableFills(new Column("gmt_create", FieldFill.INSERT))
                .addTableFills(new Column("gmt_modified", FieldFill.INSERT_UPDATE))
                .enableFileOverride();
    }

    private static void builderMapper(Mapper.Builder builder) {
        builder.enableFileOverride()
                //.enableBaseResultMap().enableBaseColumnList()
                .superClass(ExtBaseMapper.class);
    }

    private List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        genTableInfos.forEach(p -> tableNames.add(p.getName()));
        return tableNames;
    }

    private GenTableInfo getTableConfig(String tableName) {
        return genTableInfos.stream().filter(p -> p.getName().equals(tableName)).findFirst().orElse(null);
    }

    private void init() {
        genConfig.getDb().getTable().getName().forEach(name -> {
            GenTableInfo genTableInfo = new GenTableInfo();
            List<String> nameList = StrUtil.split(name, ",");
            genTableInfo.setName(getTableConfig(nameList, 0));
            genTableInfo.setIdName(getTableConfig(nameList, 1));
            genTableInfos.add(genTableInfo);
        });
    }

    private String getTableConfig(List<String> nameList, int index) {
        return nameList.size() > index ? nameList.get(index) : null;
    }

}
