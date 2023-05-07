package com.aixu.mapper;

import com.aixu.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {

    @Select("select * from db_account where username=#{nameOrEmail} or email=#{nameOrEmail}")
    Account selectAccountByEmailOrName(String nameOrEmail);

    @Insert("insert into db_account (email,username,password) values(#{email},#{username},#{password})")
    int createAccount(String username,String password,String email);
}
