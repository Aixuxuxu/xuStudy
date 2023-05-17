package com.aixu.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccountUser {
    private Integer id;
    private String username;
    private String email;
    private String avatar;
    private String backgroundImg;
    private String introduce;


//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
}
