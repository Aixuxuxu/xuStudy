package com.aixu.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

        @Resource
        private RedisTemplate<String, Object> listRedisTemplate;
        // ...
        public List<Object> getList(String key) {
            return listRedisTemplate.opsForList().range(key, 0, -1);
        }
        public Object getListItem(String key, int index) {
            return listRedisTemplate.opsForList().index(key, index);
        }
        public void leftPush(String key, Object value) {
            listRedisTemplate.opsForList().leftPush(key, value);
        }
        public void rightPush(String key, Object value) {
            listRedisTemplate.opsForList().rightPush(key, value);
        }
        public void rightAllPush(String key, Object o) {
            listRedisTemplate.opsForList().rightPushAll(key, o);
        }
        public Object leftPop(String key) {
            return listRedisTemplate.opsForList().leftPop(key);
        }
        public Object rightPop(String key) {
            return listRedisTemplate.opsForList().rightPop(key);
        }

        public void expireHours(String key, int time){
            listRedisTemplate.expire(key,time,TimeUnit.HOURS);
        }
        public void expireSecond(String key, int time){
            listRedisTemplate.expire(key,time,TimeUnit.SECONDS);
        }

        public void set(String key, Object value) {
            listRedisTemplate.opsForValue().set(key, value);
        }

        public Object get(String key) {
            return listRedisTemplate.opsForValue().get(key);
        }

        public void delete(String key) {
            listRedisTemplate.delete(key);
        }
}
