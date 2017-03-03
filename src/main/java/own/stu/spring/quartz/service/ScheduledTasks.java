package own.stu.spring.quartz.service;

import org.springframework.stereotype.Component;

@Component("scheduledTasks")
public class ScheduledTasks {

    public void print() {
        String msg = "hello";
        System.out.println(" =============== " + msg + " =============== ");
    }
}