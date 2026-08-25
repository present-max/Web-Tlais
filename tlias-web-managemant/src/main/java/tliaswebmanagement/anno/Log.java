package tliaswebmanagement.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//指明该注解作用在方法上
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)//指明该注解在运行时生效
public @interface Log {
}
