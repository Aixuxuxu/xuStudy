package com.aixu.service;

import com.aixu.entity.Pager;
import com.aixu.entity.dto.ArticleDetailsDTO;
import com.aixu.entity.dto.UserStarArticleDTO;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface ArticleService {

    /**
     * 获取所有文章
     * @return  文章集合
     */
    Pager<ArticleDetailsDTO> getAllArticle(int page, int size);


    /**
     * 创建新 文章
     *
     * @param title   文章标题
     * @param content 文章内容
     * @param userId   用户id
     * @return 1
     */
    String createArticle(String title, String content, Integer userId);

    /**
     * 通过文章id和用户id获取文章详细信息
     * @param articleId 文章id
     * @param accountId 用户id
     * @return 文章对象
     */
    ArticleDetailsDTO getArticle(Integer articleId, Integer accountId);

    ArrayList<UserStarArticleDTO> getUserStarArticle(Integer accountId);

    /**
     * 根据用户ID和文章ID修改 收藏
     * @param isStar
     * @param accountId
     * @param articleId
     * @return
     */
    String deleteArticleStar(Integer isStar, Integer accountId, Integer articleId);

    /**
     * 查询用户发布的文章
     * @param accountId 用户id
     * @return  文章集合
     */
    @Select("select * from ")
    ArrayList<ArticleDetailsDTO> getUserArticle(Integer accountId);

    int getLikeCount(Integer accountId);

    int getStarCount(Integer accountId);

    int getSelectCount(Integer accountId);

    String insertMessage(Integer accountId, Integer articleId);
}
