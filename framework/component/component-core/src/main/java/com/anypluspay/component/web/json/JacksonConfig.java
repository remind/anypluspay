package com.anypluspay.component.web.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxj
 * 2024/11/14
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
public class JacksonConfig {
}
