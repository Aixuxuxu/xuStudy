package com.aixu.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserStarArticleDTO {

    private String articleId;
    private String title;
    private String accountId;
    private String createTime;

}
