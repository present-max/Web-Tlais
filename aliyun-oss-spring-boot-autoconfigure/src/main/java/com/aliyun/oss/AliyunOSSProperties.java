package com.aliyun.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "aliyun.oss")//将配置文件中的aliyun.oss.*属性值，映射到当前这个组件中
//封装OSS的属性的类
public class AliyunOSSProperties {
    private String endpoint;
    private String bucket;
    private String region;

    public AliyunOSSProperties() {
    }

    public AliyunOSSProperties(String endpoint, String bucket, String region) {
        this.endpoint = endpoint;
        this.bucket = bucket;
        this.region = region;
    }

    /**
     * 获取
     * @return endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * 设置
     * @param endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * 获取
     * @return bucket
     */
    public String getBucket() {
        return bucket;
    }

    /**
     * 设置
     * @param bucket
     */
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    /**
     * 获取
     * @return region
     */
    public String getRegion() {
        return region;
    }

    /**
     * 设置
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    public String toString() {
        return "AliyunOSSProperties{endpoint = " + endpoint + ", bucket = " + bucket + ", region = " + region + "}";
    }
}
