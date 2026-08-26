package tliaswebmanagement.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

//令牌的生命过程：在登录成功之后，生成一个令牌，将令牌返回给前端，前端将令牌保存在localStorage中，之后访问资源时，
// 将令牌发送给后端，后端将令牌解析，获取自定义信息，判断令牌是否合法，并返回数据给前端。
public class JwtUtil {

    private static final String JWT_SECRET = "tliaswebmanagement";
    private static final long JWT_EXPIRATION = 86400000;

    //
    // 原理：其组成 Header、Payload、Signature，三段都是Base64编码字符串
    // 可直接解码查看内容，但前二者只是编码，不是加密，不能存放密码
    // Header中可存签名算法，Payload中可存用户信息，二者合并根据签名算法加密出Signature用来检验，前后保持强一对一关系，
    // 令牌检验时会读取前二者用同一算法加密出签名再与后者比较，若后者被篡改或者前者被篡改，重算出的签名都不会与后者相匹配。
    public static String generateToken(Map<String ,Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)   //签名算法与密钥
                .addClaims(claims)                                  //添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))//设置过期时间
                .compact();
    }

    //解析token
    public static Map<String, Object> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

}
