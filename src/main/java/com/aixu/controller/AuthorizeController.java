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

    /**
     * 验证码请求
     * @param email：电子邮箱
     * @param session   session
     * @return  x
     */
    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(@Pattern(regexp = EMAIL_REGEXP)
                                          @RequestParam("email")
                                              String email, HttpSession session){   // 要求必须传入 email 参数

        String s = authorityService.sendValidateEmail(email, session.getId(),false);
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


    /**
     * 重置密码_验证码请求
     * @param email 电子邮箱
     * @param session   Session
     * @return  x
     */
    @PostMapping("/validate-reset-email")
    public RestBean<String> validateResetEmail(@Pattern(regexp = EMAIL_REGEXP)
                                          @RequestParam("email")
                                          String email, HttpSession session){   // 要求必须传入 email 参数

        String s = authorityService.sendValidateEmail(email, session.getId(),true);
        if( s == null){
            // 成功
            return RestBean.success("邮箱已发送，请注意查收");
        }else {
            //        失败
            return RestBean.failure(400,s);
        }

    }


    /**
     * 重置密码第一步——邮箱验证
     * @param email 邮箱
     * @param code  验证码
     * @param session   x
     * @return x
     */
    @PostMapping("/start-reset")
    public RestBean<String> startReset(@Pattern(regexp = EMAIL_REGEXP) @RequestParam("email") String email,
                                       @Length(min = 6, max = 6) @RequestParam("code") String code,
                                       HttpSession session) {
        String s = authorityService.validateOnly(email, code, session.getId());
        if(s == null) {
            session.setAttribute("reset-password", email);
            return RestBean.success();
        } else {
            return RestBean.failure(400, s);
        }
    }

    /**
     * 重置密码请求_数据库更新密码
     * @param password  密码
     * @param session   x
     * @return  x
     */
    @PostMapping("/do-reset")
    public RestBean<String> resetPassword(@Length(min = 6, max = 16) @RequestParam("password") String password,
                                          HttpSession session){
        String email = (String) session.getAttribute("reset-password");
        if(email == null) {
            return RestBean.failure(401, "请先完成邮箱验证");
        } else if(authorityService.resetPassword(password, email)){
            session.removeAttribute("reset-password");
            return RestBean.success("密码重置成功");
        } else {
            return RestBean.failure(500, "内部错误，请联系管理员");
        }
    }

}
