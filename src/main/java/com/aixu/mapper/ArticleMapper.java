package com.aixu.mapper;

import com.aixu.entity.Article;
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
    @Select("select * from db_article where id=#{articleId}")
    Article selectById(Integer articleId);

    /**
     * 分页查询所有
     * @return  List集合
     */
    @Results({
            @Result(column = "createtime",property = "createTime")
    })
    @Select("select * from db_article limit #{page},#{size}")
    List<Article> selectAllByPager(Map<String , Object> params);

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
    @Insert("insert into db_article (title,content) values (#{title},#{content});")
    Integer insertArticle(String title,String content);

    /**
     * 根据用户ID查询所属的文章列表
     * @param accountId 用户Id
     * @return 文章集合
     */
    @Select("select * from db_article where accountId = #{accountId}")
    ArrayList<Article> selectAllByAccountId(Integer accountId);

//    @Select("select count(*) from db_article where accountId = #{accountId}")
//    int selectAllCountByAccountId(Integer accountId);



}
