package com.aixu.config;

import com.aixu.entity.RestBean;
import com.aixu.service.AuthorityService;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private AuthorityService authorityService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .anyRequest().authenticated()   // 所有请求都需要 登录
                .and()
                .formLogin()
                .loginProcessingUrl("/api/auth/login")  // 统一登录校验的接口路径
                .successHandler(this::onAuthenticationSuccess)  // 我们希望登录成功之后返回的是 JSON 格式的信息
                .failureHandler(this::onAuthenticationFailure)  // 我们希望登录失败之后返回的是 JSON 格式的信息
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")
                .and()
                .csrf().disable()   // 暂时关闭 CSRF
                .exceptionHandling()    // 设置访问页面没有权限的响应
                .authenticationEntryPoint(this::onAuthenticationFailure)    //我们希望给没有权限的用户返回 JSON 格式的信息
                .and()
                .build();
    }

    /**
     * 配置数据库验证
     * @param security  当前对象
     * @return  AuthenticationManager
     * @throws Exception    x
     */
    @Bean("authenticationManager")
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception {
       return security
               .getSharedObject(AuthenticationManagerBuilder.class)
               .userDetailsService(authorityService)
               .and()
               .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 登录成功处理
     * @param request   当前请求
     * @param response   当前响应
     * @param authentication    认证信息
     * @throws IOException  x
     * @throws ServletException x
     */
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSONObject.toJSONString(RestBean.success("登录成功！")));

    }

    /**
     * 登录失败/没有权限处理
     * @param request   当前请求
     * @param response  当前响应
     * @param exception 异常信息/失败信息
     * @throws IOException  x
     * @throws ServletException x
     */
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSONObject.toJSONString(RestBean.failure(401,exception.getMessage())));
    }
}
