package com.gd.config;

import com.gd.domain.SseData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * sse 数据存储
 */
@Configuration
public class SseDataConfig {
    @Bean(name ="sseBlockingQueue" )
    public BlockingQueue<SseData> sseBlockingQueue(){
        return  new ArrayBlockingQueue<SseData>(100);
    }
}
