package com.atguigu.statisticservice.task;

import cn.hutool.core.date.DateUtil;
import com.atguigu.statisticservice.service.StatisticsDailyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService dailyService;

    /**
     * 测试
     * 每天七点到二十三点每五秒执行一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        log.info("*********++++++++++++*****执行了");
        log.info(DateUtil.formatDate(DateUtil.yesterday()));
    }

    /**
     * 每天凌晨1点执行定时
     */
    @Scheduled(cron = "00 00 01 * * ?")
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.yesterday());
        dailyService.createStatisticsByDay(day);
    }
}
