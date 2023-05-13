package com.aixu.testmapper;

import com.aixu.entity.dto.UserStarArticleDTO;
import com.aixu.mapper.AccountAndArticleMapper;
import jakarta.annotation.Resource;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class TestAccountAndArticleMapper {
    @Resource
    AccountAndArticleMapper accountAndArticleMapper;

    @Test
    void testSelectStarArticleByUserId(){
        ArrayList<UserStarArticleDTO> userStarArticleDTOS = accountAndArticleMapper.selectStarArticleByUserId(1);
        for(var obj:userStarArticleDTOS){
            System.out.println(obj.toString());
        }
    }
}
