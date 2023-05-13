package com.aixu.service.impl;

import com.aixu.entity.Article;
import com.aixu.entity.Pager;
import com.aixu.entity.dto.ArticleDetailsDTO;
import com.aixu.entity.dto.UserStarArticleDTO;
import com.aixu.mapper.AccountAndArticleMapper;
import com.aixu.mapper.ArticleMapper;
import com.aixu.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private AccountAndArticleMapper accountAndArticleMapper;

    @Override
    public Pager<ArticleDetailsDTO> getAllArticle(int page,int size) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("page", (page-1) * size);
        params.put("size",size);

        Pager<ArticleDetailsDTO> articlePager = new Pager<>();
        List<ArticleDetailsDTO> articles = articleMapper.selectAllByPager(params);
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

    @Override
    public ArticleDetailsDTO getArticle(Integer articleId, Integer accountId) {
        ArticleDetailsDTO article = articleMapper.selectById(articleId,accountId);

        int isLikeCount = accountAndArticleMapper.selectIsLikeCountByArticleId(articleId);
        int isStarCount = accountAndArticleMapper.selectIsStarCountByArticleId(articleId);

        return article
                .setIsStarCount(isStarCount)
                .setIsLikeCount(isLikeCount);
    }

    @Override
    public ArrayList<UserStarArticleDTO> getUserStarArticle(Integer accountId) {
        ArrayList<UserStarArticleDTO> userStarArticleDTOS = accountAndArticleMapper.selectStarArticleByUserId(accountId);
        if (userStarArticleDTOS.isEmpty()) return null;

        return userStarArticleDTOS;
    }

    @Override
    public String deleteArticleStar(Integer isStar, Integer accountId, Integer articleId) {
        int i = accountAndArticleMapper.updateIsStar(isStar, accountId, articleId);
        if(i==0) return "删除失败，该文章疑似被删除或收藏，请联系管理员";
        return null;
    }
}
