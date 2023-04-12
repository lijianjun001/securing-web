package com.example.securingweb;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

import static com.example.securingweb.Constant.secretKey;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String jwtToken = req.getHeader("authorization");
        if (jwtToken==null){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        System.out.println(jwtToken);
        Jws<Claims> claimsJws= Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
        String username=claimsJws.getBody().getSubject();
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claimsJws.getHeader().get("authorities"));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(req,servletResponse);
    }
}
