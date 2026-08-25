package com.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean//当容器中没有这个组件的时候，才会创建这个组件
public class HeaderConfig {

    @Bean
    @ConditionalOnClass(HeaderParser.class)//当类路径下有这个类时，才会创建这个组件
    public HeaderParser headerParser(){
        return new HeaderParser();
    }

    @ConditionalOnProperty(prefix = "header",value = "enabled",matchIfMissing = true)//当配置文件中header.enabled=true时，才会创建这个组件
    @Bean
    public HeaderGenerator headerGenerator(){
        return new HeaderGenerator();
    }
}
