package com.aixu.service;

import com.aixu.entity.dto.CommentsDto;
import com.aixu.entity.Comments;
import java.util.ArrayList;

public interface CommentsService {


    /**
     * 添加评论
     * @param comment   评论对象
     * @return  处理结果
     */
    String insertComments(Comments comment);


    /**
     * 查询文章的所有评论
     * @param articleId 文章ID
     * @return  评论集合
     */
    ArrayList<CommentsDto> selectComments(Integer articleId);

}
