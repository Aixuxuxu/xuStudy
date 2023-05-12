package com.aixu.service;

import com.aixu.entity.Article;
import com.aixu.entity.Pager;

import java.util.List;

public interface ArticleService {

    /**
     * 获取所有文章
     * @return  文章集合
     */
    Pager<Article> getAllArticle(int page, int size);


    String createArticle(String title,String content);
}
