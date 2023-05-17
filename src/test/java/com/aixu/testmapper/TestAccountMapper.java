package com.aixu.testmapper;

import com.aixu.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestAccountMapper {

    @Resource
    AccountMapper accountMapper;

    @Test
    void testSelectAccountById(){
        System.out.println(accountMapper.selectAccountById(1));
    }

    @Test
    void testUpdateAccountAvatarById(){
        System.out.println(accountMapper.updateAccountAvatarById("123",5));
    }
    @Test
    void testUpdateAccountBackgroundById(){
        System.out.println(accountMapper.updateAccountBackgroundById("123",5));
    }
}
