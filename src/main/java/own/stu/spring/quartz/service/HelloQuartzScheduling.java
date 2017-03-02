package own.stu.spring.quartz.service;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.text.ParseException;
import java.util.Date;

/**
 * 调度任务
 */
public class HelloQuartzScheduling {

    public static void main(String[] args) throws SchedulerException, ParseException {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail jobDetail = new JobDetailImpl("helloQuartzJob", Scheduler.DEFAULT_GROUP, HelloQuartzJob.class);

        /**
         * simpleTrigger
         */
        /*SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl("simpleTrigger", Scheduler.DEFAULT_GROUP);

        simpleTrigger.setStartTime(new Date(System.currentTimeMillis()));
        simpleTrigger.setRepeatInterval(5000);
        simpleTrigger.setRepeatCount(10);

        scheduler.scheduleJob(jobDetail, simpleTrigger);*/

        /**
         * cronTrigger
         */
        String cronExpression = "3/5 * * * * ?"; // 每分钟的30s起，每5s触发任务
        CronTrigger cronTrigger = new CronTriggerImpl("cronTrigger", Scheduler.DEFAULT_GROUP, cronExpression);

        scheduler.scheduleJob(jobDetail, cronTrigger);

        scheduler.start();
    }

}