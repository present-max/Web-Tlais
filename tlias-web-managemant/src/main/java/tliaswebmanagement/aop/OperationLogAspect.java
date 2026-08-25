package tliaswebmanagement.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import tliaswebmanagement.pojo.OperateLog;
import tliaswebmanagement.mapper.OperateLogMapper;
import tliaswebmanagement.util.CurrentHolder;

import java.util.Arrays;
import java.time.LocalDateTime;

@Order(1)//当不同类有多个切面的切入点指向同一方法时，执行顺序按照类名排序，目标方法前的通知：字母排名靠前的先执行
         //目标方法后通知：字母排名靠后的先执行，也可通过 @Order 注解指定数字大小代替类名
@Aspect
@Component//必须加：这样spring才能获取这个切面类的代理对象去调用里面的方法
public class OperationLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    //环绕通知：在目标方法执行前后添加日志记录
    @Around("@annotation(tliaswebmanagement.anno.Log)")//被Log 注解的方法才会执行此通知
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin=System.currentTimeMillis();

        //获取目标方法的返回值，当目标方法没有返回值（即返回类型为 void）时，joinPoint.proceed() 会返回 null。
        Object result=joinPoint.proceed();//同时还会执行目标方法

        long end=System.currentTimeMillis();
        long cost=end-begin;

        //构建操作日志
        OperateLog log=new OperateLog();
        log.setOperateEmpId(getCurrentUserId());
        log.setOperateTime(LocalDateTime.now());
        log.setClassName(joinPoint.getTarget().getClass().getName());
        log.setMethodName(joinPoint.getSignature().getName());
        log.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        log.setReturnValue(result !=null?result.toString():"void");
        log.setCostTime(cost);

        operateLogMapper.insert(log);

        return result;

    }
    //其它通知类型：@Before、@After、@AfterReturning、@AfterThrowing，无需像 @Around 一样，手动调用 proceed() 方法，
    //这些通知类型会自动调用 proceed() 方法
    @Before("execution(void tliaswebmanagement.controller.Deptcontroller.add(tliaswebmanagement.pojo.Dept))")//前置通知：在目标方法执行之前添加日志记录
               //execution(修饰符 返回值类型 包名.类名.方法名(参数列表) throws 异常)，修饰符，包名，类名，异常可省，* 表示任意符号，..代表任意数量参数或多级包名
    public void beforeLogOperation(){
        System.out.println("before log operation");
    }
    @After("@annotation(tliaswebmanagement.anno.Log)")//后置通知：在目标方法执行之后添加日志记录
    public void afterLogOperation(){
        System.out.println("after log operation");
    }
    @AfterReturning("@annotation(tliaswebmanagement.anno.Log)")//返回通知：在目标方法返回结果之后添加日志记录
    public void afterReturningLogOperation(){
        System.out.println("after returning log operation");
    }
    @AfterThrowing("@annotation(tliaswebmanagement.anno.Log)")//异常通知：在目标方法抛出异常时添加日志记录
    public void afterThrowingLogOperation(){
        System.out.println("after throwing log operation");
    }

    private Integer getCurrentUserId(){
        return CurrentHolder.getCurrentId();
    }
}

