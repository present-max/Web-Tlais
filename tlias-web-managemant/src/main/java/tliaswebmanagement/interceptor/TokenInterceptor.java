package tliaswebmanagement.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tliaswebmanagement.util.JwtUtil;

//拦截器
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override//访问前处理，返回值true则放行，返回值false则不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取资源请求路径
        String requestURI = request.getRequestURI();
        //判断是否为登录请求
        if(requestURI.contains("/login")){
            log.info("登录请求");
            return  true;
        }
        //获取请求头中的token令牌
        String token = request.getHeader("token");
        //判断 token是否存在
        if(token == null||token.isEmpty()){
            log.info("请求头token不存在");
            response.setStatus(401);//设置状态码
            return false;
        }
        //验证token令牌
        try {
            JwtUtil.parseToken( token);  //不合法则会抛出异常
        } catch (Exception e) {
            log.info("令牌验证失败");
            response.setStatus(401);
            return false;
        }
        //检验通过放行
        log.info("令牌验证通过");
        return true;
    }
}
