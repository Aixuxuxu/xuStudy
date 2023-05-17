package com.aixu.mapper;

import com.aixu.entity.Article;
import com.aixu.entity.dto.ArticleDetailsDTO;
import com.aixu.entity.dto.UserStarArticleDTO;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {

    /**
     * 根据 文章Id 查询单个文章
     * @param articleId 文章Id
     * @return  文章信息
     */
//    @Select("select * from db_article where id=#{articleId}")
//    Article selectById(Integer articleId);
    @Select("select * from db_article da,(select * from db_account where id=#{accountId}) s where da.id=#{articleId} and da.accountId=s.id")
    ArticleDetailsDTO selectById(Integer articleId,Integer accountId);

    /**
     * 分页查询所有文章
     * @return  List集合
     */
    @Results({
            @Result(column = "createtime",property = "createTime"),
//            @Result(column = "like_account",property = "isLikeCount"),
//            @Result(column = "star_account",property = "isStarCount")
    })
//    @Select("select * from db_article limit #{page},#{size}")
//    List<Article> selectAllByPager(Map<String , Object> params);
    @Select("SELECT a.title, a.content, a.id ,a.accountId ,a.createtime, \n" +
            "    SUM(CASE WHEN aa.isLike = 1 THEN 1 ELSE 0 END) AS isLikeCount, \n" +
            "    SUM(CASE WHEN aa.isStar = 1 THEN 1 ELSE 0 END) AS isStarCount, \n" +
            "    u.username\n" +
            "FROM db_article a\n" +
            "JOIN db_account u ON a.accountId = u.id\n" +
            "LEFT JOIN account_article aa ON a.id = aa.articleId\n" +
            "GROUP BY a.id, u.username\n" +
            "ORDER BY isLikeCount DESC, isStarCount DESC " +
            "limit #{page},#{size}")
    List<ArticleDetailsDTO> selectAllByPager(Map<String , Object> params);

    /**
     * 通过文章数量
     * @return 文章数量
     */
    @Select("select count(1) from db_article ")
    long count();

    /**
     * 插入 文章
     * @param title 文章标题
     * @param content   文章内容
     * @return  影响行数
     */
    @Insert("insert into db_article (title,content,accountId) values (#{title},#{content},#{userId});")
    Integer insertArticle(String title,String content,Integer userId);

    /**
     * 根据用户ID查询所属的文章列表
     * @param accountId 用户Id
     * @return 文章集合
     */
    @Select("select * from db_article where accountId = #{accountId}")
    ArrayList<Article> selectAllByAccountId(Integer accountId);

//    @Select("select count(*) from db_article where accountId = #{accountId}")
//    int selectAllCountByAccountId(Integer accountId);

    /**
     * 查询用户发布过的所有文章
     * @param accountId 用户id
     * @return  文章集合
     */
    @Select("select id,accountId,title,content,createtime,type from db_article where accountId = #{accountId}")
    ArrayList<ArticleDetailsDTO> selectUserArticle(Integer accountId);


}
