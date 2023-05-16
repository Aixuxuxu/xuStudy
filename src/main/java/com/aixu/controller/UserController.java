package com.aixu.controller;

import com.aixu.entity.dto.AccountUser;
import com.aixu.entity.RestBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/user")
public class UserController {


    /**
     * 获取用户基本信息
     * @param accountUser   从Session中取出用户信息
     * @return  用户信息
     */
    @GetMapping("/me")
    public RestBean<AccountUser> me( @SessionAttribute("account") AccountUser accountUser){
//        return RestBean.success(JSONObject.toJSONString(accountUser));
        return RestBean.success(accountUser);

    }


}
