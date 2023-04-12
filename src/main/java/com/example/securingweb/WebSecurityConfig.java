package com.example.securingweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

import static com.example.securingweb.Constant.secretKey;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login").successHandler((request, response, authentication) -> {

                            String jwt = Jwts.builder()
                                    .claim("authorities", "UERR")//配置用户角色
                                    .setSubject(request.getParameter("username"))
                                    .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                                    .signWith(secretKey)
                                    .compact();
                            response.setContentType("application/json;charset=utf-8");
                            PrintWriter out = response.getWriter();
                            out.write(new ObjectMapper().writeValueAsString(jwt));
                            out.flush();
                            out.close();
                        })
                        .permitAll()
                )
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyInUserDetailsManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5");
    }
}
