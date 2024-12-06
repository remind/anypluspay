package com.anypluspay.channel;

import cn.hutool.setting.yaml.YamlUtil;
import com.anypluspay.component.generator.DalGenerator;
import com.anypluspay.component.generator.config.GenConfig;

import java.io.File;

/**
 * @author wxj
 * 2024/8/1
 */
public class ChannelDalGenerator {
    public static void main(String[] args) {
        GenConfig genConfig = YamlUtil.loadByPath("config.yaml", GenConfig.class);
        DalGenerator dalGenerator = new DalGenerator(genConfig);
        File f = new File(ChannelDalGenerator.class.getResource("/").getPath());
        String projectPath = f.getParentFile().getParentFile().getParentFile().getParentFile().getPath() + "/app/infra/infra-dal";
        dalGenerator.execute(projectPath, "");
    }
}
