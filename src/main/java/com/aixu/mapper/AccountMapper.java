package com.aixu.mapper;

import com.aixu.entity.Account;
import com.aixu.entity.dto.AccountUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
}
