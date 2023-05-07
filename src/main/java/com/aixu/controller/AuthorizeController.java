package com.aixu.controller;

import com.aixu.entity.RestBean;
import com.aixu.service.AuthorityService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    static final String EMAIL_REGEXP = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";   // 验证邮箱的正则
    static final String USERNAME_REGEXP = "^[a-zA-Z0-9_一-龥]+$";


    @Resource
    private AuthorityService authorityService;

    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(@Pattern(regexp = EMAIL_REGEXP)
                                          @RequestParam("email")
                                              String email, HttpSession session){   // 要求必须传入 email 参数

        String s = authorityService.sendValidateEmail(email, session.getId());
        if( s == null){
            // 成功
            return RestBean.success("邮箱已发送，请注意查收");
        }else {
            //        失败
            return RestBean.failure(400,s);
        }

    }

    /**
     * 用户注册
     * @param username 用户名
     * @param password  密码
     * @param email 邮箱
     * @param code  验证码
     * @param session  SessionId
     * @return RestBean
     */
    @PostMapping("/registerUse")
    public RestBean<String> registerUser( @Length(min = 2,max = 8) @Pattern(regexp = USERNAME_REGEXP) @RequestParam("username") String username,
                                         @Length(min = 6,max = 16) @RequestParam("password") String password,
                                         @Pattern(regexp = EMAIL_REGEXP)@RequestParam("email") String email,
                                         @Length(min = 6, max = 6) @RequestParam("code") String code,
                                          HttpSession session){
        String s = authorityService.validateAndRegister(username, password, email, code, session.getId());
        if (s == null)
            return RestBean.success("注册成功");
        else
            return RestBean.failure(400,s);

    }

}
