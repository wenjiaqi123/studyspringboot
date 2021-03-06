package com.gsm.job;

import com.gsm.websocket.WebSocketServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

//主要用于标记配置类，兼备Component的效果
@Configuration
/**
 * 开启定时任务
 */
@EnableScheduling
public class MainJob {

    /**
     * 添加定时任务，
     */
    @Scheduled(cron = "0 0/1 0 * * ?")
    public void myJob(){
        System.out.println(new Date());
        System.out.println("--发送信息给客户端-");
        WebSocketServer.sendInfoAll(new Date().toString(),"20");
    }
}
