package com.aixu.task;

import com.aixu.entity.dto.ArticleDetailsDTO;
import com.aixu.mapper.ArticleMapper;
import com.aixu.utils.RedisUtil;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
@Slf4j
public class ScheduleTask {

    @Resource
    RedisUtil redisUtil;

    @Resource
    ArticleMapper articleMapper;

//    @Scheduled(cron = "0 0 12 * * ?")   // 每天中午12点
    @Scheduled(cron = "*/30 * * * * *")   // 每天10s
    public void redisByTopList(){
//        log.info("10s到了");
        HashMap<String, Object> params = new HashMap<>();
        String key = "topList";
        params.put("page", 0);
        params.put("size",10);
        ArrayList<ArticleDetailsDTO> list = articleMapper.selectAllByPager(params);

        String json = JSON.toJSONString(list);
        redisUtil.set(key,json);
        redisUtil.expireSecond(key,30);
    }
}
