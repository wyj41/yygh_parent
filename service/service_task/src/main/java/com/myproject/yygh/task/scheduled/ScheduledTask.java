package com.myproject.yygh.task.scheduled;

import com.myproject.common.rabbit.constant.MqConst;
import com.myproject.common.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTask {
    @Autowired
    private RabbitService rabbitService;

    //每天8点执行方法，提醒就医
    //cron表达式，执行时间间隔
    //每天8点：0 0 8 * * ?
    @Scheduled(cron = "0/30 * * * * ?")
    public void taskPatient(){
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK,MqConst.ROUTING_TASK_8,"");
    }
}
