package com.aixu.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ArticleDetailsDTO {
    private String id;
    private String accountId;
    private String username;
    private String title;
    private String content;
    private String createTime;
    private Integer isLikeCount;
    private Integer isStarCount;
    private String type;
}
