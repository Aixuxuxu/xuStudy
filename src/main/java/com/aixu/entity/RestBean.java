package com.aixu.entity;

import lombok.Data;

@Data
public class RestBean <T> {

    private Integer code;   // 状态嘛
//    private String message;
    private Boolean state;  // 状态
    private T message;         // JSON 对象

    private RestBean(Integer code, Boolean state, T message) {
        this.code = code;
        this.state = state;
        this.message = message;
    }

    public static <T> RestBean<T> success(){
        return new RestBean<>(200,true,null);
    }

    public static <T> RestBean<T> success(T message){
        return new RestBean<>(200,true,message);
    }


    public static <T> RestBean<T> failure(Integer code){
        return new RestBean<>(code,false,null);
    }

    public static <T> RestBean<T> failure(Integer code,T message){
        return new RestBean<>(code,false,message);
    }
}
