package com.aixu.controller;

import com.aixu.entity.RestBean;
import com.aixu.entity.dto.AccountUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @PostMapping("/updateAvatar")
    public RestBean<AccountUser> updateAvatar(@RequestParam("accountId") String id,
                                              @RequestParam("avatar") String avatar){

        try {

            AccountUser accountUser = new AccountUser();
            accountUser.setAvatar(avatar);
            return RestBean.success(accountUser);
        }catch (Exception e){
            e.getStackTrace();
            return RestBean.failure(401);
        }
    }

    @PostMapping("/updateBackgroundImg")
    public RestBean<AccountUser> updateBackgroundImg(@RequestParam("accountId") String id,
                                              @RequestParam("backgroundImg") String backgroundImg){

        try {

            AccountUser accountUser = new AccountUser();
            accountUser.setBackgroundImg(backgroundImg);
            return RestBean.success(accountUser);
        }catch (Exception e){
            e.getStackTrace();
            return RestBean.failure(401);
        }
    }
}
