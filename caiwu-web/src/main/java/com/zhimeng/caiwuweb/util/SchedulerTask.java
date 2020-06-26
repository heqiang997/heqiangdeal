package com.zhimeng.caiwuweb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SchedulerTask {
    private Logger logger = LoggerFactory.getLogger(SchedulerTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private int count = 0;

    @Autowired
    StartRunner runner;

    @Scheduled(cron="0 30 2 * * ?")
    private void process(){
        //每晚2点半执行更新出差模板,以防数据更新
        runner.setTemplate();
        logger.info("this is scheduler task runing  "+(count++));
    }

    @Scheduled(fixedRate = 6000)
    public void reportCurrentTime() {
        logger.info("现在时间：" + dateFormat.format(new Date()));
    }
}
