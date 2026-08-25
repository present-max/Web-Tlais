package tliaswebmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tliaswebmanagement.interceptor.TokenInterceptor;

@Configuration//标记为配置类
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( tokenInterceptor)
                .addPathPatterns("/**");
               // .excludePathPatterns("/login");//将某些访问路径排除在外
    }
}
