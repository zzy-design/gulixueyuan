package com.atguigu.serurity.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * token管理
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Component
public class TokenManager {

    private long tokenExpiration = 24*60*60*1000;
    private String tokenSignKey = "123456";

    public String createToken(String username) {
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
    }

    public String getUserFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
    }

    public void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

    public static void main(String[] args) {
        TokenManager tokenManager = new TokenManager();
        String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ." +
                "H4sIAAAAAAAAAKtWKi5NUrJSSkzJzcxT0lFKrShQsjI0MzUxsbQ0NzOsBQBBfhniIAAAAA." +
                "vuOrN57dsRy0UD5o650kH6lnb6X8QVRaq1Vtk_SVa8UWzSyoh9Quhv4GUu9rClFb-zc1X6VmpSrfRQgoNG9otQ";
        System.out.println(tokenManager.getUserFromToken(token));
    }
}
