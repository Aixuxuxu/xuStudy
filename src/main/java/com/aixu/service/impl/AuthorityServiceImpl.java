package com.aixu.service.impl;

import com.aixu.entity.Account;
import com.aixu.mapper.AccountMapper;
import com.aixu.service.AuthorityService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private MailSender mailSender;

    @Resource
    private StringRedisTemplate template;

    @Value("${spring.mail.username}")
    String from;



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

    /**
     * 如何实现邮箱发送验证码？
     * - 1，先生成对应的验证码
     * - 2. 把邮箱和对应的验证码直接放到 Redis 里面（过期时间）
     * - 3。发送验证码到指定邮箱
     * - 4. 如果发送失败，把 Redis 里面的刚刚插入的删除
     * - 5. 用户在注册时，再从Redis里面取出对应键值对，然后看验证码是否一致
     *
     * @param email ：邮箱
     * @param sessionId    SessionId
     * @return boolean
     */
    @Override
    public boolean sendValidateEmail(String email, String sessionId) {
        String key = "email:" + sessionId + ":" + email;

        // 获取 验证码 的间隔为两分钟
        if(Boolean.TRUE.equals(template.hasKey(key))){
            Long expire = Optional.ofNullable(template.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if(expire > 120){
                return false;
            }
        }

        // 邮件设置
        Random random = new Random();
       int code  = random.nextInt(899999) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("邮件验证码提醒");
        message.setText("验证码是：" + code);
        try{

            // 将 邮箱+session+验证码存入 redis 中,过期时间设定为 3 分钟
            template.opsForValue().set(key,String.valueOf(code),3, TimeUnit.MINUTES);

            // 发送邮件
            mailSender.send(message);
            return true;
        }catch (MailException e){
            e.printStackTrace();
            return false;
        }

    }
}
