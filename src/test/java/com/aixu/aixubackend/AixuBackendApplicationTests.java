package com.aixu.aixubackend;

import com.aixu.entity.Article;
import com.aixu.entity.dto.ArticleDetailsDTO;
import com.aixu.mapper.ArticleMapper;
import com.aixu.service.ArticleService;
import jakarta.annotation.Resource;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class AixuBackendApplicationTests {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleService articleService;

    @Test
    void contextLoads() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("126127"));
    }

    @Test
    void testInsertArticle(){
//        System.out.println(articleService.createArticle("小星星变奏曲", "第一步。。。。。。", userId));
    }
    @Test
    void testSelectArticle(){
        List<Article> articles = articleMapper.selectAllByAccountId(1);
        for(var obj : articles){
            System.out.println(obj.toString());
        }
        System.out.println(articleMapper.selectAllByAccountId(1).size());
    }

    @Test
    void testGetArticle(){
        ArticleDetailsDTO article = articleService.getArticle(25, 1);
        System.out.println(article);
    }

    @Test
    void test(){
        System.out.println(10%10);
        System.out.println(11%10);
        System.out.println(12%10);
    }

}
