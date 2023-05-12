package com.aixu.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccountAndArticle {
    private Integer id ;
    private Integer isLike;
    private Integer isStar;
    private Integer accountId;
    private Integer articleId;
}
