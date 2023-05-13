package com.aixu.controller;

import com.aixu.entity.Article;
import com.aixu.entity.RestBean;
import com.aixu.entity.dto.ArticleDetailsDTO;
import com.aixu.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;
//    @Resource
//    private ArticleService articleService;

    @GetMapping("/getAllArticle")
    public RestBean<List<Article>> getAllArticle(){

        return RestBean.success(articleService.getAllArticle(1, 10).getRows());
    }
    @PostMapping("/createArticle")
    public RestBean<String> createArticle(@RequestParam("title") String title,
                                          @RequestParam("content") String content){

        String s = articleService.createArticle(title,content);
        if( s == null){
            return RestBean.success("文章发布成功");
        }

        return RestBean.failure(401,s);
    }

    @GetMapping("/getArticle")
    public RestBean<ArticleDetailsDTO> getArticle(@RequestParam("articleId") Integer articleId,
                                       @RequestParam("accountId") Integer accountId){
        if(articleId==null || accountId==null)return RestBean.failure(401);
        ArticleDetailsDTO articleDetailsDTO = articleService.getArticle(articleId, accountId);
        return RestBean.success(articleDetailsDTO);
    }
}
