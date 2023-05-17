package com.aixu.controller;

import com.aixu.entity.RestBean;
import com.aixu.entity.dto.AccountUser;
import com.aixu.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Base64;

import static com.aixu.controller.AuthorizeController.EMAIL_REGEXP;
import static com.aixu.controller.AuthorizeController.USERNAME_REGEXP;

@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Resource
    private AccountService accountService;

    @PostMapping("/updateAvatar")
    public RestBean<String> updateAvatar(@RequestParam("accountId") Integer id,
                                              @RequestParam("avatar") String avatar){
        if (id == null || avatar.isEmpty()) return RestBean.failure(401);

        try {
//            StringBuffer sb = new StringBuffer();
//            sb.append(avatar);
            String s = accountService.updateAccountAvatarById(id, avatar);
            if(s==null) return RestBean.success("更新头像成功");
            else return RestBean.failure(401,"头像更新失败，请联系管理员");
        }catch (Exception e){
            e.getStackTrace();
            return RestBean.failure(401);
        }
    }

    @PostMapping("/updateBackgroundImg")
    public RestBean<String> updateBackgroundImg(@RequestParam("accountId") Integer id,
                                              @RequestParam("backgroundImg") String backgroundImg){

        if (id == null || backgroundImg.isEmpty()) return RestBean.failure(401);

        try {
            String s = accountService.updateAccountBackgroundById(id, backgroundImg);
            if(s==null) return RestBean.success("更新背景成功");
            else return RestBean.failure(401,"背景更新失败，请联系管理员");
        }catch (Exception e){
            e.getStackTrace();
            return RestBean.failure(401);
        }
    }

    /**
     * 根据用户id查用户
     * @param accountId 用户id
     * @return  用户信息
     */
    @GetMapping("/getAccount")
    public RestBean<AccountUser> getAccount(@RequestParam("accountId") Integer accountId){
        if (accountId == null) return RestBean.failure(401);
        try {

            return RestBean.success(accountService.selectAccountById(accountId));

        }catch (Exception e){
            return RestBean.failure(401);
        }
    }


    /**
     * 根据用户id查用户
     * @param accountId 用户id
     * @return  用户信息
     */
    @PostMapping("/updateUser")
    public RestBean<String> updateUser( @RequestParam("accountId") Integer accountId,
                                        @Pattern(regexp = USERNAME_REGEXP)  @RequestParam("username") String username,
                                        @Pattern(regexp = EMAIL_REGEXP)     @RequestParam("email") String email,
                                        String introduce){
        if (accountId == null || username.isEmpty() || email.isEmpty()) return RestBean.failure(401,"用户名或邮箱不能为空");
        try {
           AccountUser accountUser  = new AccountUser()
                            .setId(accountId)
                            .setUsername(username)
                            .setEmail(email)
                            .setIntroduce(introduce);

            String s = accountService.updateUser(accountUser);
            if(s==null)  return RestBean.success("更新用户信息成功");
            else return RestBean.failure(401,s);
        }catch (Exception e){
            return RestBean.failure(401,"异常，请联系管理员");
        }
    }


}
