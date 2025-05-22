package com.anypluspay.admin;

import cn.hutool.setting.yaml.YamlUtil;
import com.anypluspay.component.generator.DalGenerator;
import com.anypluspay.component.generator.config.GenConfig;

import java.io.File;

/**
 * @author wxj
 * 2025/5/22
 */
public class AdminDalGenerator {

    public static void main(String[] args) {
        GenConfig genConfig = YamlUtil.loadByPath("config.yaml", GenConfig.class);
        DalGenerator dalGenerator = new DalGenerator(genConfig);
        File f = new File(AdminDalGenerator.class.getResource("/").getPath());
        String projectPath = f.getParentFile().getParentFile().getParentFile().getParentFile().getPath() + "/app/dal";
        dalGenerator.execute(projectPath, "");
    }
}
