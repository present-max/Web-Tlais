package com.aliyun.oss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@EnableConfigurationProperties(AliyunOSSProperties.class)//相当于是第五种方法，@（自定义注释，自定义注释中加上（@import(AliyunOSSProperties.class)））
@Configuration
public class AliyunOssAutoConfiguration {//要在指定的META-INE配置文件中添加全类名，才能生效
    @Bean
    @ConditionalOnMissingBean
    public AliyunOSSOperator aliyunOSSOperator( AliyunOSSProperties aliyunOSSProperties){
        return new AliyunOSSOperator( aliyunOSSProperties);
    }
}
