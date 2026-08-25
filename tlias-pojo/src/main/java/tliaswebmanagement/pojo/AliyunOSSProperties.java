package tliaswebmanagement.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")//将配置文件中的aliyun.oss.*属性值，映射到当前这个组件中
//封装OSS的属性的类
public class AliyunOSSProperties {
    private String endpoint;
    private String bucket;
    private String region;

}
