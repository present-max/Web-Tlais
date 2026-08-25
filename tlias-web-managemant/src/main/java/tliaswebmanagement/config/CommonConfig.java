package tliaswebmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tliaswebmanagement.util.AliyunOSSOperator;

@Configuration//标记为配置类

public class CommonConfig {

    @Bean//第三方类的实例化，并交给Spring管理，此处时假设AliyunOSSOperator是第三方类（无法在源码上加@Component）
    public AliyunOSSOperator aliyunOSSOperator(){//若AliyunOSSOperator类对象的创建要求参数，则此处的参数要加上，
        return new AliyunOSSOperator();          //若该参数要依赖bean容器中其他bean，则该参数要加上@Autowired注解（可省略）
    }

}
