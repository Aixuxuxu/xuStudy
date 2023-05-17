package com.aixu.service.impl;

import com.aixu.entity.dto.AccountUser;
import com.aixu.mapper.AccountMapper;
import com.aixu.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    AccountMapper accountMapper;

    @Override
    public AccountUser selectAccountById(Integer accountId) {
        try {

            return accountMapper.selectAccountById(accountId);

        }catch (Exception e){
            e.getStackTrace();
            return null;
        }
    }


    @Override
    public String updateAccountAvatarById(Integer accountId, String avatar) {
        try {
            int i = accountMapper.updateAccountAvatarById( avatar,accountId);
            if(i > 0){
                return null;
            }
            return "更新失败，请联系管理员";
        }catch (Exception e){
            e.getStackTrace();
            return "更新失败，请联系管理员";
        }
    }

    @Override
    public String updateAccountBackgroundById(Integer id, String backgroundImg) {
        try {
            int i = accountMapper.updateAccountBackgroundById(backgroundImg,id);
            if(i > 0){
                return null;
            }

            return "更新失败，请联系管理员";
        }catch (Exception e){
            e.getStackTrace();
            return "更新失败，请联系管理员";
        }
    }

    @Override
    public String updateUser(AccountUser accountUser) {
        try{
            int i = accountMapper.updateAccountById(accountUser.getId(),accountUser.getUsername(),accountUser.getEmail(),accountUser.getIntroduce());
            if(i > 0) return null;
            else return "跟新个人信息失败，请联系管理员";
        }catch (Exception e){
            e.getStackTrace();
            return "跟新个人信息失败，请联系管理员";
        }
    }
}
