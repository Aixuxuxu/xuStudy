package com.aixu.mapper;

import com.aixu.entity.Comments;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface CommentsMapper {

    /**
     * 添加评论
     * @param comments ： 评论对象
     * @return  1
     */
    @Insert("insert into db_comments (parentId,content,accountId,articleId,isDelete) values (#{comments.parentId},#{comments.content},#{comments.accountId},#{comments.articleId},0)")
    int insertComment(@Param("comments") Comments comments);

    /**
     * 通过Id查询评论信息
     * @param id 评论id
     * @return 对象
     */
    @Select("select * from db_comments where id=#{id}")
    Comments selectCommentsById(Integer id);

    /**
     * 通过 父节点 id查询子评论
     * @param parentId  父评论ID
     * @return 集合
     */
//    @Select("select * from db_comments where parentId=#{parentId} ")
    @Select("select c.* , da.username ,da.avatar  from db_comments c right join db_account da on c.accountId = da.id where parentId=#{parentId}   ")
    ArrayList<Comments> selectCommentsByParentId(Integer parentId);


    /**
     * 通过文章id和用户id查询评论信息
     * @param accountId 用户id
     * @param articleId 文章ID
     * @return 对象
     */
    @Select("select * from db_comments where accountId=#{accountId} and articleId=#{articleId}")
    Comments selectCommentsByAccountIdAndArticleId(Integer accountId,Integer articleId);


    @Select("select * from db_comments where articleId=#{articleId} and parentId=0")
    ArrayList<Comments> selectCommentByArticleId(Integer articleId);


}
