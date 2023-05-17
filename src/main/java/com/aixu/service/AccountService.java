package com.aixu.service;

import com.aixu.entity.dto.AccountUser;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

public interface AccountService {

    /**
     * 根据用户id查用户
     * @param accountId 用户id
     * @return  用户信息
     */
    AccountUser selectAccountById(Integer accountId);


    /**
     * 更新用户头像
     * @param accountId 用户id
     * @param avatar    头像base64
     * @return  影响数
     */
    String  updateAccountAvatarById(Integer accountId,String avatar);

    String updateAccountBackgroundById(Integer id, String backgroundImg);

    String updateUser(AccountUser accountUser);
}
