package own.stu.spring.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.text.ParseException;


/**
 * Created by dell on 2017/3/3.
 */
@Configuration
@ComponentScan("own.stu.spring.quartz.service")// 扫描任务所在包
public class SchedledConfiguration {
    //律译通支付回调
    @Bean
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean_payNotifyLytAPP() {
        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
        obj.setTargetBeanName("scheduledTasks");
        obj.setTargetMethod("print");
        return obj;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean_payNotifyLytAPP(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(methodInvokingJobDetailFactoryBean_payNotifyLytAPP().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("print");
        stFactory.setGroup("print");
        stFactory.setCronExpression("0/3 * * * * ? ");
        return stFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(cronTriggerFactoryBean_payNotifyLytAPP().getObject());
        return scheduler;
    }
}
