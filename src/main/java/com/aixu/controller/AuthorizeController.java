package com.aixu.controller;

import com.aixu.entity.RestBean;
import com.aixu.service.AuthorityService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
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

    @Resource
    private AuthorityService authorityService;

    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(@Pattern(regexp = EMAIL_REGEXP)
                                          @RequestParam("email")
                                              String email, HttpSession session){   // 要求必须传入 email 参数
        if(authorityService.sendValidateEmail(email,session.getId())){
            // 成功
            return RestBean.success("邮箱已发送，请注意查收");
        }else {
            //        失败
            return RestBean.failure(400,"邮箱发送失败，请联系管理员");
        }

    }
}
