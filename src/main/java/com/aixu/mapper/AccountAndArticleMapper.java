package com.aixu.mapper;

import com.aixu.entity.AccountAndArticle;
import com.aixu.entity.dto.UserStarArticleDTO;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AccountAndArticleMapper {

    /**
     * 插入用户和文章之间的联系
     * @param accountId 用户id
     * @param articleId 文章id
     * @return  影响行数
     */
    @Insert("insert into account_article (accountId,articleId) values (#{accountId},#{articleId})")
    int insert(Integer accountId, Integer articleId);

    /**
     * 查询该表中是否有该映射
     * @param accountId 用户id
     * @param articleId   文章id
     * @return
     */
    @Select("select accountId,articleId,isLike,isStar from account_article where accountId=#{accountId} and articleId=#{articleId} ")
    AccountAndArticle selectMap(Integer accountId,Integer articleId);

    /**
     * 更新点赞
     * @param isLike    点赞： 0/1
     * @param accountId 用户Id
     * @param articleId 文章ID
     * @return 影响行数
     */
    @Update("update account_article set isLike = #{isLike} where accountId=#{accountId} and articleId=#{articleId}")
    int updateIsLike(Integer isLike,Integer accountId,Integer articleId);

    /**
     * 更新收藏
     * @param isStar    收藏： 0/1
     * @param accountId 用户Id
     * @param articleId 文章ID
     * @return 影响行数
     */
    @Update("update account_article set isStar = #{isStar} where accountId=#{accountId} and articleId=#{articleId}")
    int updateIsStar(Integer isStar,Integer accountId,Integer articleId);


    /**
     * 根据 用户ID或文章ID 查询所有
     * @param id    用户id/文章id
     * @return  文章集合
     */
    @Select("select * from account_article where accountId=#{id} or articleId=#{id}")
    List<AccountAndArticle> selectAllIsLikeById(Integer id);

    /**
     * 根据 用户ID 查询用户所浏览的文章数量
     * 根据 文章ID 查询该文章的浏览量
     * @param id    用户id/文章id
     * @return  统计数量
     */
    @Select("select count(*) from account_article where accountId=#{id} or articleId=#{id}")
    int selectAllCountById(Integer id);

    /**
     * 根据文章ID查询 被点赞数
     * @param articleId 文章ID
     * @return 统计数量
     */
    @Select("select count(*) from account_article where isLike=1 and articleId=#{articleId}")
    int selectIsLikeCountByArticleId(Integer articleId);

    /**
     * 根据文章ID查询 被收藏数
     * @param articleId 文章ID
     * @return 统计数量
     */
    @Select("select count(*) from account_article where isStar=1 and articleId=#{articleId}")
    int selectIsStarCountByArticleId(Integer articleId);


    /**
     * 根据用户 Id 查询用户收藏过的所有文章
     * @param accountId 用户ID
     * @return  文章集合
     */
    @Select("select * " +
            "from db_article article,(select * from account_article where accountId=#{accountId} and isStar=1) s " +
            "where article.id = s.articleId")
    ArrayList<UserStarArticleDTO> selectStarArticleByUserId(Integer accountId);

    @Select("select count(*) from account_article where accountId=#{accountId}")
    int getSelectCount(Integer accountId);


    @Delete("delete from account_article where articleId=#{articleId}")
    int deleteMap(Integer articleId);
}
