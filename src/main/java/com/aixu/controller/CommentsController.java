package com.aixu.controller;

import com.aixu.entity.Comments;
import com.aixu.entity.RestBean;
import com.aixu.entity.dto.CommentsDto;
import com.aixu.service.CommentsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comment/")
public class CommentsController {

    @Resource
    private CommentsService commentsService;

    /**
     * 添加评论
     * @param parentId  评论父ID
     * @param content   内容
     * @param accountId 用户Id
     * @param articleId 文章ID
     * @return s
     */
    @PostMapping("/addComment")
    public RestBean<String> addComment(@RequestParam("parentId") Integer parentId,
                                       @RequestParam("content") String content,
                                       @RequestParam("accountId") Integer accountId,
                                       @RequestParam("articleId") Integer articleId
                                       ){
        if (parentId == null || content.isEmpty() || accountId == null || articleId == null) return RestBean.failure(400,"参数错误");
        String s = commentsService.insertComments(new Comments()
                .setParentId(parentId)
                .setContent(content)
                .setAccountId(accountId)
                .setArticleId(articleId)
        );
        if (s==null) return RestBean.success("评论发表成功");
        else return RestBean.success(s);
    }

    @GetMapping("/getComments")
    public RestBean<List<CommentsDto>> getComments(@RequestParam("articleId") Integer articleId){
        if (articleId == null) return RestBean.failure(400);
        ArrayList<CommentsDto> commentsDtos = commentsService.selectComments(articleId);
        return RestBean.success(commentsDtos);
    }
}
