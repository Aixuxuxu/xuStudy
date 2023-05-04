package com.aixu.service;

import com.aixu.entity.Account;
import com.aixu.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthorityService implements UserDetailsService {

    @Resource
    private AccountMapper accountMapper;

    /**
     * 构造 USerDetail 对象
     * @param username  ：用户名/邮箱
     * @return  USerDetail
     * @throws UsernameNotFoundException    x
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null){throw new UsernameNotFoundException("用户名或邮箱不能为空");}
        Account account = accountMapper.selectAccountByEmailOrName(username);
        if (account==null) throw new UsernameNotFoundException("用户名或密码错误");
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }
}
