package com.aixu.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Article {
    private Integer id;
    private String title;
    private String content;
    private String createTime;
    private Integer accountId;

}
