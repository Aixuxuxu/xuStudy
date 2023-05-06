package com.aixu.service;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface AuthorityService extends UserDetailsService {
    boolean sendValidateEmail(String email, String id);
}
