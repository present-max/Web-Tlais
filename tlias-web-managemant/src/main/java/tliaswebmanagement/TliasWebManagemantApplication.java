package tliaswebmanagement;

import org.apache.http.message.TokenParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;


//springboot自动配置的原理
//SpringBootApplication里面封装了@ComponentScan(默认是扫描当前包及其子包)、@SpringBootConfiguration
//(里面封装了Configuration,将启动类也标记为配置类),@EnableAutoConfiguration(里面封装了@import(里面
// 导入了一个Importselector接口的实现类，该类重写了selectImport方法，返回了一个装有需导入bean的所有类的全类名
// 的数组，import将每一个元素导入ioc容器，这些全类名就封装在META-INE的配置文件中，该配置文件中本来就有一些spring官方写好的类，写代码的时候可直接用
// @Autowied获取，若要想ioc中加入自定义的bean或第三方bean，可向配置文件中加全类名，也可直接import导入，不过归根结底都是通过import导入的))、
//注：只有被spring扫描管理的类中的bean才能被注入，包括@Component、@Service、@Repository、@Controller及其派生注解，还有配置类中的@Bean
@ServletComponentScan//扫描Servlet组件(filter)
//springboot自动配置的方法
//1
@ComponentScan(basePackages = {"tliaswebmanagement", "com.aliyun.oss"})//指定扫描的包，默认是当前包及其子包
//2
//@Import(TokenParser.class)//普通类的bean
//3
//@Import(HeaderConfig.class)配置类的bean（导入配置类中的所有bean）
//4
//@Import(MyImportSelector.class)ImportSelector的实现类的bean
//5@EnableHeaderConfig（这个注解中有import）
@SpringBootApplication
public class TliasWebManagemantApplication {

    public static void main(String[] args) {
        SpringApplication.run(TliasWebManagemantApplication.class, args);
    }

}
