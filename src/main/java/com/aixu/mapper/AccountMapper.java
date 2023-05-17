package com.aixu.mapper;

import com.aixu.entity.Account;
import com.aixu.entity.dto.AccountUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper {

    @Select("select * from db_account where username=#{nameOrEmail} or email=#{nameOrEmail}")
    Account selectAccountByEmailOrName(String nameOrEmail);

    @Select("select * from db_account where username=#{nameOrEmail} or email=#{nameOrEmail}")
    AccountUser selectAccountUserByEmailOrName(String nameOrEmail);

    @Insert("insert into db_account (email,username,password) values(#{email},#{username},#{password})")
    int createAccount(String username,String password,String email);

    @Update("update db_account set password = #{password} where email = #{email}")
    int resetPasswordByEmail(String password, String email);


    /**
     * 根据用户id查用户
     * @param accountId 用户id
     * @return  用户信息
     */
    @Select("select * from db_account where id = #{accountId}")
    AccountUser selectAccountById(Integer accountId);

    /**
     * 更新用户头像
     * @param accountId 用户id
     * @param avatar    头像base64
     * @return  影响数
     */
    @Update("update db_account set avatar = #{avatar} where id = #{accountId}")
    int updateAccountAvatarById(@Param("avatar") String avatar, @Param("accountId") Integer accountId);
    /**
     * 更新用户头像
     * @param accountId 用户id
     * @param backgroundImg    背景base64
     * @return  影响数
     */
    @Update("update db_account set backgroundImg = #{backgroundImg} where id = #{accountId}")
    int updateAccountBackgroundById(@Param("backgroundImg") String backgroundImg, @Param("accountId") Integer accountId);


    /**
     * 修改用户信息
     * @param accountId 用户id
     * @param username  用户名
     * @param email 邮箱
     * @param introduce 个人简介
     * @return  影响行数
     */
    @Update("update db_account set username=#{username},email=#{email},introduce=#{introduce} where id=#{accountId}")
    int updateAccountById(Integer accountId,String username,String email,String introduce);
}
