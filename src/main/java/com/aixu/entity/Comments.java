package com.aixu.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Comments {
    private Integer id;
    private Integer parentId;
    private String  content;
    private Integer accountId;
    private Integer articleId;
    private String createTime;
    private Integer isDelete;

    private String username;
//    private String avatar;

}
