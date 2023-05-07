package com.aixu.service;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface AuthorityService extends UserDetailsService {
    String sendValidateEmail(String email, String id);

    String  validateAndRegister(String username, String password, String email, String code, String sessionId);
}
