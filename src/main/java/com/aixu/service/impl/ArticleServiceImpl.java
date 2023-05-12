package com.aixu.service.impl;

import com.aixu.entity.Article;
import com.aixu.entity.Pager;
import com.aixu.mapper.ArticleMapper;
import com.aixu.service.ArticleService;
import jakarta.annotation.Resource;
import lombok.var;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.SimpleFormatter;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Pager<Article> getAllArticle(int page,int size) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("page", (page-1) * size);
        params.put("size",size);

        Pager<Article> articlePager = new Pager<>();
        List<Article> articles = articleMapper.selectAllByPager(params);
        articlePager.setRows(articles);
        articlePager.setTotal(articleMapper.count());

        return articlePager;
    }

    @Override
    public String createArticle(String title, String content) {
        if(articleMapper.insertArticle(title,content) > 0){
            // 插入成功
            return null;
        }
        // 插入失败
        return "文章发布失败，请联系管理员";
    }
}
