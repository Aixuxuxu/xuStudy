package com.aixu.interceptor;

import com.aixu.entity.Account;
import com.aixu.entity.AccountUser;
import com.aixu.mapper.AccountMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizeInterceptor implements HandlerInterceptor {

    @Resource
    AccountMapper accountMapper;

    /**
     * 拦截器
     * 将登录的 用户信息 存储到Session 中
     * @param request   当前请求
     * @param response  当前响应
     * @param handler   e
     * @return  true/false
     * @throws Exception x
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、获取 上下文对象
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        // 2、获取当前请求的 验证对象，该对象存储了用户的用户名
        User user = (User)authentication.getPrincipal();
        String username = user.getUsername();

        // 3、使用用户名进行查询，并设置到 Session 中
        AccountUser account = accountMapper.selectAccountUserByEmailOrName(username);
        request.getSession().setAttribute("account",account);

        return true;
    }
}
