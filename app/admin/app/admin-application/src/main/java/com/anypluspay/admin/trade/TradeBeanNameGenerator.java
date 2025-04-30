package com.anypluspay.admin.trade;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * 由于mapper存在类名存在重复，如RefundOrderMapper，需要将注册bean名称区分开，否则会冲突
 * @author wxj
 * 2025/4/29
 */
public class TradeBeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        String originalBeanName = super.generateBeanName(beanDefinition, registry);
        return "trade" + StringUtils.capitalize(originalBeanName);
    }
}
