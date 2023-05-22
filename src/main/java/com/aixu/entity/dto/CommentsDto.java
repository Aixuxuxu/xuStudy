package com.aixu.entity.dto;

import com.aixu.entity.Comments;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class CommentsDto {

    private Integer id;
    private Integer parentId;
    private String  content;
    private Integer accountId;
    private Integer articleId;
    private String createTime;
//    private Integer isDelete;
    private ArrayList<Comments> childComments;
    private String avatar;
    private String userName;
//    private Integer isLike;
}
