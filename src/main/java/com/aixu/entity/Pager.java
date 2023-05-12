package com.aixu.entity;

import lombok.Data;

import java.util.List;

/**
 * 分页查询对象
 * @param <T>
 */
@Data
public class Pager<T> {
    private int page;   // 分页起始页
    private int size;   // 每页记录数
    private List<T> rows;   // 返回的记录集合
    private long total; // 总记录条数

}
