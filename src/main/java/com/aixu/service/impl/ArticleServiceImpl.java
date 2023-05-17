package com.aixu.service.impl;

import com.aixu.entity.AccountAndArticle;
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
    public String createArticle(String title, String content, Integer userId) {
        if(articleMapper.insertArticle(title,content,userId) > 0){
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


    @Override
    public int getLikeCount(Integer accountId) {
        return accountAndArticleMapper.selectIsLikeCountByArticleId(accountId);
    }

    @Override
    public int getStarCount(Integer accountId) {
        return accountAndArticleMapper.selectIsStarCountByArticleId(accountId);
    }

    @Override
    public ArrayList<ArticleDetailsDTO> getUserArticle(Integer accountId) {

        return articleMapper.selectUserArticle(accountId);
    }

    @Override
    public int getSelectCount(Integer accountId) {
        return accountAndArticleMapper.getSelectCount(accountId);
    }

    @Override
    public String insertMessage(Integer accountId, Integer articleId) {
        AccountAndArticle accountAndArticle = accountAndArticleMapper.selectMap(accountId, articleId);
        if (accountAndArticle == null){
            int insert = accountAndArticleMapper.insert(accountId, articleId);
            if(insert > 0) return null;
            else return "更新失败";
        }

        return null;
    }
}
