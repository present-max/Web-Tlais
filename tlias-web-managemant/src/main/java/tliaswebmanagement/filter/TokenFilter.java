package tliaswebmanagement.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import tliaswebmanagement.util.CurrentHolder;
import tliaswebmanagement.util.JwtUtil;

import java.io.IOException;
import java.util.Map;

//filter的作用范围>intercepter
@Slf4j
@WebFilter( urlPatterns = "/*")//拦截所有请求
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取资源请求路径
        String requestURI = request.getRequestURI();
        //判断是否为登录请求
        if(requestURI.contains("/login")){
            log.info("登录请求");
            filterChain.doFilter(request,response);//直接放行,放行后，会访问对应的资源，访问完之后，代码会继续往下执行
            return;
        }
        //获取请求头中的token令牌
        String token = request.getHeader("token");
        //判断 token是否存在
        if(token == null||token.isEmpty()){
            log.info("请求头token不存在");
            response.setStatus(401);//设置状态码
            return;
        }
        //验证token令牌
        try {
            Map<String, Object> claim=  JwtUtil.parseToken( token);  //不合法则抛出异常,获取令牌中第二部分的信息
            Integer id = Integer.valueOf(claim.get("id").toString());
            CurrentHolder.setCurrentId(id);
            log.info("当前用户id为:"+id);
        } catch (Exception e) {
            log.info("令牌验证失败");
            response.setStatus(401);
            return;
        }
        //检验通过放行
        log.info("令牌验证通过");
        filterChain.doFilter(request,response);//放行到下一个过滤器，若已是最后一个过滤器，则放行到资源，过滤器的拦截顺序时根据拦截器字符串类名排序

        //移除当前线程中保存的当前用户id
        CurrentHolder.remove();
    }
}
