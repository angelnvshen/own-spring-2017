package own.stu.spring.quartz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import own.stu.spring.quartz.service.SpringTimerService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * spring的定时器
 */
@Component
@Configurable
//@EnableScheduling //标注启动定时任务。
public class ScheduledTasks {

    @Scheduled(fixedRate = 1000 * 3)
    public void reportCurrentTime() {
        System.out.println("Scheduling Tasks Examples: The time is now " + dateFormat().format(new Date()));
        springTimerService.print("hello");
    }

    //每1分钟执行一次
    @Scheduled(cron = "*/5 * *  * * * ")
    public void reportCurrentByCron() {
        System.out.println("Scheduling Tasks Examples By Cron: The time is now " + dateFormat().format(new Date()));
    }

    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

    @Autowired
    private SpringTimerService springTimerService;
}