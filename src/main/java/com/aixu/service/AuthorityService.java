package com.aixu.service;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface AuthorityService extends UserDetailsService {
    String sendValidateEmail(String email, String id,Boolean haAccount);

    String  validateAndRegister(String username, String password, String email, String code, String sessionId);

    String validateOnly(String email, String code, String sessionId);

    /**
     * 对数据库的密码进行重置
     * @param password  密码
     * @param email 邮箱
     * @return  true/false
     */
    boolean resetPassword(String password, String email);

}
