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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public String validateOnly(String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email + ":true";
        if(Boolean.TRUE.equals(template.hasKey(key))) {
            String s = template.opsForValue().get(key);
            if(s == null) return "验证码失效，请重新请求";
            if(s.equals(code)) {
                template.delete(key);
                return null;
            } else {
                return "验证码错误，请检查后再提交";
            }
        } else {
            return "请先请求一封验证码邮件";
        }
    }

    @Override
    public boolean resetPassword(String password, String email) {
            password = bCryptPasswordEncoder.encode(password);
            return accountMapper.resetPasswordByEmail(password, email) > 0;
    }


    @Override
    public String validateAndRegister(String username, String password, String email, String code,String sessionId) {
        String key = "email:" + sessionId + ":" + email;

        // 查询 Redis 数据库是否存在 key
        if(Boolean.TRUE.equals(template.hasKey(key))) {
            String s = template.opsForValue().get(key);
            if(s==null){return "验证码失效，请重新请求";}
            if(s.equals(code)){
                Account a = accountMapper.selectAccountByEmailOrName(username);
                if(a != null) return "此用户名已被注册，请更换用户名";
                template.delete(key);
                password = bCryptPasswordEncoder.encode(password);  // 对密码进行加密

                // 验证码相同,插入到数据库中
                int account = accountMapper.createAccount(username, password, email);
                if(account > 0){
                    return null;
                }else {
                    return "内部错误，请联系管理员";
                }
            } else {
                // 验证码不相同
                return "验证码错误，请检查后再提交";
            }
        }

            return "请先完成邮箱验证";
    }

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
    public String sendValidateEmail(String email, String sessionId,Boolean hasAccount) {
        String key = "email:" + sessionId + ":" + email + ":" + hasAccount;

        // 获取 验证码 的间隔为两分钟
        if(Boolean.TRUE.equals(template.hasKey(key))){
            Long expire = Optional.ofNullable(template.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if(expire > 120){
                return "请求频繁";
            }
        }
        Account account = accountMapper.selectAccountByEmailOrName(email);
        // 判断邮箱是否存在
        if(hasAccount && account == null) return "没有此邮件地址的账户";
        if(!hasAccount && account != null) return "此邮箱已被其他用户注册";
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
            return null;
        }catch (MailException e){
            e.printStackTrace();
            return "验证码发送失败，请检查邮箱是否有效";
        }

    }
}
