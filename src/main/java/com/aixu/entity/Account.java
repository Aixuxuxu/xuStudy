package com.aixu.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.sql.In;

@Data
@Accessors(chain = true)
public class Account {
    private Integer id;
    private String email;
    private String username;
    private String password;
}
