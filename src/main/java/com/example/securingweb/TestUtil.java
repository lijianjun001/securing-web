package com.example.securingweb;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class TestUtil {
    public static void main(String args[]){
        String key = "0123456789_0123456789_0123456789";
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        String jwt = Jwts.builder()
                .claim("authorities", "USER")//配置用户角色
                .setSubject("user")
                .signWith(secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .compact();
        System.out.println(jwt);

        String username= Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody().getSubject();
        System.out.println(username);
        Object authorities= Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody().get("authorities");
        System.out.println(authorities);
        Object exp= Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody().get("exp");
        System.out.println(exp);
    }
}
