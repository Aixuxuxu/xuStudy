package com.aixu.service.impl;

import com.aixu.entity.Comments;
import com.aixu.entity.dto.AccountUser;
import com.aixu.entity.dto.CommentsDto;
import com.aixu.mapper.CommentsMapper;
import com.aixu.service.AccountService;
import com.aixu.service.CommentsService;
import jakarta.annotation.Resource;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Resource
    private CommentsMapper commentsMapper;

    @Resource
    private AccountService accountService;

    @Override
    public String insertComments(Comments comments) {

        int i = commentsMapper.insertComment(comments);
        if(i>0) return null;
        else return "插入失败，请联系管理员";
    }

    @Override
    public ArrayList<CommentsDto> selectComments(Integer articleId) {
        // 返回该文章中所有的父评论
        ArrayList<Comments> comments = commentsMapper.selectCommentByArticleId(articleId);
        if (comments.size() == 0) return null;
        ArrayList<CommentsDto> commentsArr = new ArrayList<CommentsDto>();
        for(var obj:comments){
            AccountUser accountUser = accountService.selectAccountById(obj.getAccountId());
            commentsArr.add(new CommentsDto()
                    .setId(obj.getId())
                    .setParentId(0)
                    .setCreateTime(obj.getCreateTime())
                    .setAccountId(obj.getAccountId())
//                    .setIsDelete(obj.getIsDelete())
                    .setContent(obj.getContent())
                    .setArticleId(obj.getArticleId())
                    .setChildComments(commentsMapper.selectCommentsByParentId(obj.getId()))
                    .setAvatar(accountUser.getAvatar())
                    .setUserName(accountUser.getUsername())
            );

        }
        return commentsArr;
    }
}
