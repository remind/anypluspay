package com.anypluspay.payment.dalgen;


import cn.hutool.setting.yaml.YamlUtil;
import com.anypluspay.component.generator.DalGenerator;
import com.anypluspay.component.generator.config.GenConfig;

import java.io.File;

/**
 * mybatis自动生成代码
 */
public class PaymentDalGenerator {

    public static void main(String[] args) {
        GenConfig genConfig = YamlUtil.loadByPath("config.yaml", GenConfig.class);
        DalGenerator dalGenerator = new DalGenerator(genConfig);
        File f = new File(PaymentDalGenerator.class.getResource("/").getPath());
        String projectPath = f.getParentFile().getParentFile().getParentFile().getParentFile().getPath() + "/app/infra/infra-dal";
        dalGenerator.execute(projectPath, "");
    }
}
