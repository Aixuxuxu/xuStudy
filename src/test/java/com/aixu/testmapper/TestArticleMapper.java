package com.aixu.testmapper;

import com.aixu.mapper.AccountAndArticleMapper;
import com.aixu.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestArticleMapper {

    @Resource
    AccountMapper accountMapper;


}
