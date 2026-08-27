package tliaswebmanagement.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tliaswebmanagement.pojo.Result;


//
@Slf4j
@RestControllerAdvice//统一处理异常
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e){
        log.error("服务器发生异常:{}",e.getMessage());
        e.printStackTrace();
        return Result.error("服务器异常");//返回给出现异常的controller三层架构
    }

    //捕获DuplicateKeyException(数据库数据重复异常)
    @ExceptionHandler   //当有多个异常处理器可处理抛出来的异常，则从该异常往上找父类异常，直到找到一个匹配的异常处理器,即匹配最相关的异常
    public Result handleException(DuplicateKeyException e){
        log.error("服务器发生异常:{}",e.getMessage());
        String message = e.getMessage();//获取异常信息
        int i=message.lastIndexOf("Duplicate enrty");
        String errmsg=message.substring(i);
        String [] arr= errmsg.split(" ");
        return Result.error(arr[2]+"当前数据已存在");
    }
}
