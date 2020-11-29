package com.dgl.timeTask;

import com.dgl.common.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 定时任务
 */
@Configuration
@EnableScheduling
public class TimeTask {

    @Value("${expire_date}")
    public long expireDate;


    /**
     * 每隔10分钟扫描一次数据库
     */
    @Scheduled(cron = Constants.EverydayAM8)
    public void configureTasks() {
        System.out.println("============现在开始执行定时任务=============");
        //获取当前时间B
        LocalDateTime now = LocalDateTime.now();
        System.out.println("UserEmail表本来就是空的........");

    }
}
