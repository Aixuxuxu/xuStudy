package com.aixu.service.impl;

import com.aixu.entity.AccountAndArticle;
import com.aixu.entity.Article;
import com.aixu.entity.Pager;
import com.aixu.entity.dto.ArticleDetailsDTO;
import com.aixu.entity.dto.UserStarArticleDTO;
import com.aixu.mapper.AccountAndArticleMapper;
import com.aixu.mapper.ArticleMapper;
import com.aixu.service.ArticleService;
import com.aixu.utils.RedisUtil;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

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
        if(article == null)return null;

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
        AccountAndArticle accountAndArticle = accountAndArticleMapper.selectMap(accountId, articleId);
        if (accountAndArticle == null) return "该文章疑不存在，请联系管理员";
        int oldIsStar = accountAndArticle.getIsStar();
        int i;
        if(oldIsStar == isStar){
            // 相等说明是取消
            i = accountAndArticleMapper.updateIsStar(0,accountId,articleId);
        }else{
            // 不相等说明是添加
            i = accountAndArticleMapper.updateIsStar(isStar, accountId, articleId);
        }
        if(i>0) return null;

        return "操作失败，请联系管理员";
    }
    @Override
    public String deleteArticleLike(Integer isLike, Integer accountId, Integer articleId) {
        AccountAndArticle accountAndArticle = accountAndArticleMapper.selectMap(accountId, articleId);
        if (accountAndArticle == null) return "该文章疑不存在，请联系管理员";
        int oldIsLike = accountAndArticle.getIsLike();
        int i;
        if(oldIsLike == isLike){
            // 相等说明是取消
            i = accountAndArticleMapper.updateIsLike(0,accountId,articleId);
        }else{
            // 不相等说明是添加
            i = accountAndArticleMapper.updateIsLike(isLike, accountId, articleId);
        }
        if(i>0) return null;

        return "操作失败，请联系管理员";
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
    public String deleteArticle(Integer accountId, Integer articleId) {
        // 1、删除该文章
        int i = articleMapper.deleteArticle(accountId,articleId);
        // 2、删除用户和该文章的联系

        if(i>0){
            accountAndArticleMapper.deleteMap(articleId);
            return null;
        }else {
            return "删除失败";
        }
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


    @Override
    public String updateArticle(Article article) {

        int  i = articleMapper.updateArticleById(article.getTitle(),article.getContent(),article.getId());
        if (i>0) return null;
        else return "更新失败，请联系管理员";
    }

    @Override
    public List<ArticleDetailsDTO> getTopArticle() {
        //1、查询redis中是否有该top数据
        String key = "topList";
        String o = (String) redisUtil.get(key);
        List<ArticleDetailsDTO> articleDetailsDTOS = JSON.parseArray(o, ArticleDetailsDTO.class);
//        articleDetailsDTOS.forEach(System.out::println);
        if (articleDetailsDTOS.size() != 0) {
            return articleDetailsDTOS;
        }else {
            // 没有就查数据库
            HashMap<String, Object> params = new HashMap<>();
            params.put("page", 0);
            params.put("size", 10);
            articleDetailsDTOS =  articleMapper.selectAllByPager(params);
        }

        return articleDetailsDTOS;
    }
}
